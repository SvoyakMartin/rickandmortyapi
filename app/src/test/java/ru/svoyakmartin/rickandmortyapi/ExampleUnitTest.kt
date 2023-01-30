package ru.svoyakmartin.rickandmortyapi

import io.reactivex.observers.TestObserver
import org.junit.Test

import org.junit.Assert.*
import ru.svoyakmartin.rickandmortyapi.data.HomeTask

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testTaskOne() {
        val observable = HomeTask.task1()
        val observer = TestObserver<Int>()
        observable.subscribe(observer)
        observer.assertComplete()
        observer.assertNoErrors()

//        observer.assertValueCount(10)
//        observer.assertValues(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

//        observer.assertValueCount(5)
//        observer.assertValues(2, 4, 6, 8, 10)
//
//        observer.assertValueCount(1)
//        observer.assertValues(6)
//
        observer.assertValueCount(0)
    }

    @Test
    fun testTaskTwo() {
        val items1 = listOf("0", "2", "4", "6", "8")
        val items2 = listOf("0", "1", "3", "5", "7", "9")
        val observable = HomeTask.task2(items1, items2)
        val observer = TestObserver<String>()
        observable.subscribe(observer)
        observer.assertComplete()
        observer.assertNoErrors()

//        observer.assertValueCount(11)
//        observer.assertValues("0", "2", "4", "6", "8", "0", "1", "3", "5", "7", "9")

//        observer.assertValueCount(10)
//        observer.assertValues("0", "2", "4", "6", "8", "1", "3", "5", "7", "9")

        observer.assertValueCount(10)
        observer.assertValues(
            "0".hashCode().toString(),
            "2".hashCode().toString(),
            "4".hashCode().toString(),
            "6".hashCode().toString(),
            "8".hashCode().toString(),
            "1".hashCode().toString(),
            "3".hashCode().toString(),
            "5".hashCode().toString(),
            "7".hashCode().toString(),
            "9".hashCode().toString())

    }
}