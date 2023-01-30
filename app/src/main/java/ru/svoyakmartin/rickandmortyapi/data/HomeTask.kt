package ru.svoyakmartin.rickandmortyapi.data

import io.reactivex.Observable

object HomeTask {

    /**
     * 1. Создайте Observable, который будет эмитить числа от 1 до 10
     * 2. Преобразуйте Observable так, чтобы он эмитил только четные числа
     * 3. Преобразуйте Observable так, чтобы он эмитил только числа, которые делятся на 3
     * 4. Преобразуйте Observable так, чтобы он эмитил только числа, которые делятся на 3 и 5
     */
    fun task1(): Observable<Int> {
        return Observable.range(1, 10)
            .filter { it % 2 == 0 }
            .filter { it % 3 == 0 }
            .filter { it % 5 == 0 }
    }

    /**
     * 1. Создайте два Observable из соответсвующих последовательностей
     * 2. Объедините два Observable в один
     * 3. Преобразуйте Observable так, чтобы он эмитил только уникальные элементы
     * 4. Преобразуйте Observable так, чтобы он эмитил только хэш коды элементов
     */
    fun task2(
        items1: List<String>,
        items2: List<String>
    ): Observable<String> {
        val observableOne = Observable.fromIterable(items1)
        val observableTwo = Observable.fromIterable(items2)

        return Observable.merge(observableOne, observableTwo)
            .distinct()
            .map { it.hashCode()}
            .map { it.toString() }

    }
}