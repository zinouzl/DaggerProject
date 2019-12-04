package com.example.daggerproject.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

public class ViewModelProviderFactory implements ViewModelProvider.Factory {


    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;
    @Inject
    public ViewModelProviderFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
        this.creators = creators;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        Provider<? extends ViewModel> creator = creators.get(modelClass);
        if (creator == null){

            for(Map.Entry<Class<? extends ViewModel>,Provider<ViewModel>> entry: creators.entrySet()){

                if (modelClass.isAssignableFrom(entry.getKey())){
                    creator = entry.getValue();
                }

            }
        }

        if (creator == null){
            throw  new IllegalArgumentException("the model Class is unknown!"+modelClass);
        }
        try {
            return (T) creator.get();
        }catch (Exception ex){
            throw new RuntimeException();
        }


    }
}
