package com.example.kotlin_stady.learn;

import com.example.kotlin_stady.util.LogUtil;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class S {

    public static void fromArray() {
        Integer array[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        Observable.fromArray(array).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
