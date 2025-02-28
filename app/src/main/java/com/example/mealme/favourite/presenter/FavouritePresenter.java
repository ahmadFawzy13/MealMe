package com.example.mealme.favourite.presenter;

import android.annotation.SuppressLint;

import com.example.mealme.favourite.view.FavouriteMealViewer;
import com.example.mealme.model.remote.Meal;
import com.example.mealme.model.repo.Repository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavouritePresenter {

    Repository repo;
    FavouriteMealViewer favouriteMealViewer;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestoreDb;
    FirebaseUser firebaseUser;
    String userId;

    public FavouritePresenter(Repository repo, FavouriteMealViewer favouriteMealViewer) {
        this.repo = repo;
        this.favouriteMealViewer = favouriteMealViewer;
        firebaseAuth = FirebaseAuth.getInstance();
        firestoreDb = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

    }

    @SuppressLint("CheckResult")
    public void getFavMealLocal(){
         repo.getAllFavLocalMeals()
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(
                         item -> favouriteMealViewer.onFavouriteMealSuccess(item),
                         error -> favouriteMealViewer.onFavouriteMealFailure(error.getLocalizedMessage())
                 );

    }

    public void deleteFavMeal(Meal meal){
        repo.deleteFavMealLocal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        favouriteMealViewer.onFavMealDeletionSuccess("Meal Deleted");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        favouriteMealViewer.onFavMealDeletionFailure(e.getLocalizedMessage());
                    }
                });
    }

    public void deleteFavMealFirebase(Meal meal){
        userId = firebaseUser.getUid();
        firestoreDb.collection("users")
                .document(userId)
                .collection("meals")
                .whereEqualTo("idMeal",meal.getIdMeal())
                .get()
                .addOnSuccessListener(itemToDelete -> {
                    for (QueryDocumentSnapshot document : itemToDelete) {
                        document.getReference().delete();
                    }
                });
    }
}
