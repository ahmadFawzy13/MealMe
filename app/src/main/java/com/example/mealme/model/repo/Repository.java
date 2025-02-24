package com.example.mealme.model.repo;

import androidx.lifecycle.LiveData;

import com.example.mealme.calendar.model.CalendarMeal;
import com.example.mealme.meal_details.model.Meal;
import com.example.mealme.model.local.MealLocalDataSource;
import com.example.mealme.model.remote.MealDetailsNetworkCallBack;
import com.example.mealme.model.remote.MealRemoteDataSource;
import com.example.mealme.model.remote.NetworkCallBack;
import com.example.mealme.model.remote.RandomMealNetworkCallBack;

import java.util.List;

public class Repository {

    private MealRemoteDataSource remoteSource;
    private MealLocalDataSource localSource;
    private static Repository repo = null;

    private Repository(MealRemoteDataSource remoteSource, MealLocalDataSource localSource) {
        this.remoteSource = remoteSource;
        this.localSource = localSource;
    }

    public static Repository getInstance(MealRemoteDataSource remoteSource, MealLocalDataSource localSource){
        if(repo == null){
            repo = new Repository(remoteSource,localSource);
        }
        return repo;
    }

    public LiveData<List<Meal>> getAllFavLocalMeals(){
        return localSource.getAllFavMeals();
    }
    public void insertFavMealLocal(Meal meal){localSource.insertMeal(meal);}
    public void deleteFavMealLocal(Meal meal){localSource.deleteMeal(meal);}
    public void getRandomRemoteMeal(RandomMealNetworkCallBack randomMealNetworkCallBack){
        remoteSource.makeRandomNetworkCall(randomMealNetworkCallBack);
    }
    public void getHomeRemoteMeals(NetworkCallBack networkCallBack){
        remoteSource.makeNetWorkCall(networkCallBack);
    }
    public void getMealDetailsRemote(MealDetailsNetworkCallBack mealDetailsNetworkCallBack, String mealId){
        remoteSource.makeMealDetailNetworkCall(mealDetailsNetworkCallBack,mealId);
    }

    public LiveData<List<CalendarMeal>> getCalendarLocalMealsByDate(String date){
        return localSource.getCalendarMealsByDate(date);
    }
    public void insertCalendarMealLocal(CalendarMeal calendarMeal){localSource.insertCalendarMeal(calendarMeal);}
    public void deleteCalendarMealLocal(CalendarMeal calendarMeal){localSource.deleteCalenderMeal(calendarMeal);}
}
