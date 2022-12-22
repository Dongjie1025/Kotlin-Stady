package com.example.kotlin_stady.learn

import com.example.kotlin_stady.util.LogUtil
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.lang.NullPointerException
import java.util.concurrent.FutureTask
import java.util.concurrent.TimeUnit
import kotlin.math.log

class LearnRxjava private constructor() {

    private lateinit var observer: Observer<Any>

    companion object {
        val instance: LearnRxjava by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            LearnRxjava()
        }
    }

    init {
        createObserver()
    }

    private fun createObserver() {
        observer = object : Observer<Any> {
            override fun onSubscribe(d: Disposable) {
                LogUtil.d("==============onSubscribe==============")
            }

            override fun onError(e: Throwable) {
                LogUtil.d("==============onError==============")
            }

            override fun onComplete() {
                LogUtil.d("==============onComplete==============")
            }

            override fun onNext(t: Any) {
                var logStr = ""
                if (t is String) {
                    logStr = t
                } else if (t is Int) {
                    logStr = t.toString()
                } else if (t is Long) {
                    logStr = t.toString()
                } else if (t is Array<*>) {
                    LogUtil.d("元素数量：${t.size}")
                    t.forEach {
                        LogUtil.d("====元素 ： $it")
                    }
                } else if (t is IntArray) {
                    LogUtil.d("元素数量：${t.size}")
                    t.forEach {
                        LogUtil.d("====元素 ： $it")
                    }
                } else if (t is ArrayList<*>) {
                    LogUtil.d("元素数量：${t.size}")
                    t.forEach {
                        LogUtil.d("====元素 ： $it")
                    }
                    return
                } else {
                    LogUtil.d("没有找到该类型数～")
                }
                LogUtil.d("==============onNext:: $logStr==============")
            }

        }
    }

    /**
     *  创建Observable（被观察者对象）
     */
    fun create() {
        Observable.create(ObservableOnSubscribe<String> {
            it.onNext("hello Observable")
            it.onComplete()
        }).subscribe(observer)
    }

    /**
     * 创建Observable并发送事件（事件数量不超过10个以上）
     */
    fun just() {
        Observable.just(1, 2, 3)
            .subscribe(observer)
    }

    /**
     * 同上但事件数量可以超过10个并且入参可以传入一个数组
     */
    fun fromArray() {
        Observable.fromArray(intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 10, 11)).subscribe(observer)
        Observable.fromArray(arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 10, 11)).subscribe(observer)
    }

    /**
     * 与Runnable用法基本一致，只是会返回一个结果并发送给观察者
     */
    fun callable() {
        Observable.fromCallable { "call !!!" }.subscribe(observer)
    }

    /**
     * 增加对Callable的操作方法如run（）、cancel（）
     */
    fun fromFuture() {
        val futureTask: FutureTask<String> = FutureTask { "返回结果" }
        Observable.fromFuture(futureTask)
            .doOnSubscribe {
//                futureTask.run()
                futureTask.cancel(true)
            }.subscribe(observer)
    }

    /**
     * 发送一个list数据给观察者
     */
    fun fromIterable() {
        var list = listOf(1, 2, 3, 4, 5)
        Observable.fromIterable(list)
            .subscribe(observer)
    }

    /**
     * 只有在订阅后才会创建被观察者
     */
    fun defer() {
        var i = 100
        val observable: Observable<Int> = Observable.defer { Observable.just(i) }
        i = 200
        observable.subscribe(observer)
        i = 300
        observable.subscribe(observer)
    }

    /**
     * 延迟发送事件
     */
    fun timer() {
        Observable.timer(2, TimeUnit.SECONDS)
            .subscribe(observer)
    }

    /**
     * 间隔发送事件，可指定延迟触发第一次发送，默认间隔7次
     */
    fun interval() {
        Observable.interval(10, 4, TimeUnit.SECONDS)
            .subscribe(observer)
    }

    /**
     * 同上，可指定初始数值start，指定间隔数量count
     */
    fun intervalRange() {
        Observable.intervalRange(10, 10, 1, 1, TimeUnit.SECONDS)
            .subscribe(observer)
    }

    /**
     * 发送范围事件即初始数值start至(start+count）的所有数值
     */
    fun range() {
        Observable.range(10, 5)
            .subscribe(observer)
    }

    /**
     * 同上但入参为long类型
     */
    fun rangeLong() {
        Observable.rangeLong(1L, 10L)
            .subscribe(observer)
    }

    /**
     * empty 不走onNext
     * never 不走onNext、onComplete
     * error 只走onError
     */
    fun emptyOrNeverOrError() {
        Observable.empty<Any>()
            .subscribe(observer)
        Observable.never<Any>()
            .subscribe(observer)
        Observable.error<Any>(NullPointerException("error"))
            .subscribe(observer)
    }

    /**
     * 转换被观察者发送的数据类型
     */
    fun map() {
        Observable.just(1, 3)
            .map { t -> "t = $t" }
            .subscribe(observer)
    }

    /**
     * 同上，不同的是flatmap返回的是一个被观察者对象Observable,可以继续进行转换
     */
    fun flatMap() {
        data class Plan(var count: Int, var action: List<String>)
        data class Person(var name: String, var plan: List<Plan>)

        val personList = ArrayList<Person>()
        val planList = ArrayList<Plan>()
        val actionList = ArrayList<String>()
        for (i in 1..2) {
            actionList.add("动作$i")
            planList.add(Plan(i, actionList))
            personList.add(Person("张$i", planList))
        }
        Observable.fromIterable(personList)
            .flatMap { t -> Observable.fromIterable(t.plan) }
            .flatMap { t -> Observable.fromIterable(t.action) }
            .subscribe(observer)
    }

    /**
     * 同上，不同的是flatmap发射事件是无序，concatMap是有序
     */
    fun concatMap() {
        data class Plan(var count: Int, var action: List<String>)
        data class Person(var name: String, var plan: List<Plan>)

        val personList = ArrayList<Person>()
        val planList = ArrayList<Plan>()
        val actionList = ArrayList<String>()
        for (i in 1..2) {
            actionList.add("动作$i")
            planList.add(Plan(i, actionList))
            personList.add(Person("张$i", planList))
        }

        Observable.fromIterable(personList)
            .concatMap { t -> Observable.fromIterable(t.plan) }
            .concatMap { t -> Observable.fromIterable(t.action) }
            .subscribe(observer)
    }

    /**
     * 创建缓存池发送一定量的事件，一次性发射的事件数量不能超过缓存池size，skip是指如果缓存池满了下一次发送的startIndex为当前的startIndex+skip
     */
    fun buffer() {
        Observable.fromArray(1, 2, 3, 4, 5, 6, 7, 8, 9)
            .buffer(3, 2)
            .subscribe(observer)
    }

    /**
     * 合并多个被观察者对象
     */
    fun concat() {
        Observable.concat(
            Observable.just(1, 2),
            Observable.just(2, 3),
            Observable.just(4, 5),
            Observable.just(5, 6))
            .subscribe(observer)
    }

    /**
     * 同上，concat是串行，merge是并行
     */
    fun merge() {
        Observable.merge(
            Observable.interval(1, 2, TimeUnit.SECONDS).map { t -> "A : $t" },
            Observable.interval(1, 3, TimeUnit.SECONDS).map { t -> "B : $t" }
        ).subscribe(observer)
    }

    /**
     * 压缩发射事件数量为被观察者中最少的数量，即两个及以上被观察者的发射事件数量强制相同并且数量为发射数量最少的那个
     */
    fun zip() {
        Observable.zip(Observable.just(1, 3, 5, 6, 7, 8),
            Observable.just(3, 2, 4, 5).map {
                t -> "B : $t"
            }
        ) { t1, t2 -> "t1 : $t1 ===== t2 : $t2"}
            .subscribe(observer)
    }

    /**
     * 延迟发射事件
     */
    fun delay(){
        Observable
            .just(1,3,4,5)
            .delay(5,TimeUnit.SECONDS)
            .subscribe(observer)
    }

    fun doOnEach(){
        Observable.just(1,3,4)
            .doOnEach {
                t -> LogUtil.d("doOnEach :: ${t.value}")
            }
            .subscribe(observer)
    }



}