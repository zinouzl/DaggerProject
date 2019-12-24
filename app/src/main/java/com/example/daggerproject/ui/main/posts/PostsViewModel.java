package com.example.daggerproject.ui.main.posts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.daggerproject.SessionManager;
import com.example.daggerproject.model.Post;
import com.example.daggerproject.model.User;
import com.example.daggerproject.retrofit.main.MainApi;
import com.example.daggerproject.ui.auth.AuthResources;
import com.example.daggerproject.ui.main.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PostsViewModel extends ViewModel {
    // TODO: Implement the ViewModel


    private static final String TAG = "PostsViewModel";

    private MediatorLiveData<Resource<List<Post>>> posts;

    private final SessionManager sessionManager;

    private final MainApi mainApi;


    @Inject
    public PostsViewModel(SessionManager sessionManager, MainApi mainApi) {
        this.mainApi = mainApi;
        this.sessionManager = sessionManager;
    }

    public LiveData<Resource<List<Post>>> getPosts() {
        if (posts == null) {
            posts = new MediatorLiveData<>();
            posts.setValue(Resource.loading((List<Post>) null));
            final LiveData<Resource<List<Post>>> source = LiveDataReactiveStreams.fromPublisher(
                    mainApi.getPostsOfUser(sessionManager.getId())
                            .subscribeOn(Schedulers.io())
                            .onErrorReturn(new Function<Throwable, List<Post>>() {
                                @Override
                                public List<Post> apply(Throwable throwable) throws Exception {
                                    Post post = new Post();
                                    post.setId(-1);
                                    ArrayList<Post> posts = new ArrayList<>();
                                    posts.add(post);
                                    return posts;
                                }
                            })
                            .map(new Function<List<Post>, Resource<List<Post>>>() {
                                @Override
                                public Resource<List<Post>> apply(List<Post> posts) throws Exception {
                                    if (posts.size() > 0) {
                                        if (posts.get(0).getId() == -1)
                                            return Resource.error("could not connect", (List<Post>) null);

                                    }
                                    return Resource.success(posts);
                                }
                            })


            );
            posts.addSource(source, new Observer<Resource<List<Post>>>() {
                @Override
                public void onChanged(Resource<List<Post>> listResource) {
                    posts.setValue(listResource);
                    posts.removeSource(source);
                }
            });
        }

        return posts;
    }


    public LiveData<AuthResources<User>> getAuthenticatedUser() {
        return sessionManager.getAuthData();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        posts = null;
    }
}
