package com.yjp.demo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 终端操作
 */
public class StreamDemo2 {
    public static void main(String[] args) {

        List<String> list = Arrays.asList("Java", "JavaScript", "python", "PHP", "C#", "Golang", "Swift");
        //anyMatch方法用于判断流中是否有符合判断条件的元素，返回值为boolean类型。比如判断list中是否含有SQL元素：
        list.stream()
                .anyMatch(s -> "SQL".equals(s)); // false
        //allMatch方法用于判断流中是否所有元素都满足给定的判断条件，返回值为boolean类型。比如判断list中是否所有元素长度都不大于10：
        list.stream()
                .allMatch(s -> s.length() <= 10); // true
        //noneMatch方法用于判断流中是否所有元素都不满足给定的判断条件，返回值为boolean类型。比如判断list中不存在长度大于10的元素：
        list.stream()
                .noneMatch(s -> s.length() > 10); // true
        //findAny方法用于返回流中的任意元素的Optional类型，例如筛选出list中任意一个以J开头的元素，如果存在，则输出它：
        list.stream()
                .filter(s -> s.startsWith("J"))
                .findAny()
                .ifPresent(System.out::println); // Java
        //findFirst方法用于返回流中的第一个元素的Optional类型，例如筛选出list中长度大于5的元素，如果存在，则输出第一个：
        list.stream()
                .filter(s -> s.length() > 5)
                .findFirst()
                .ifPresent(System.out::println); // JavaScript
        //reduce函数从字面上来看就是压缩，缩减的意思，它可以用于数字类型的流的求和，求最大值和最小值。如对numbers中的元素求和：
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
                .reduce(0, Integer::sum); // 16
        //reduce函数也可以不指定初始值，但这时候将返回一个Optional对象，比如求最大值和最小值：
        numbers.stream()
                .reduce(Integer::max)
                .ifPresent(System.out::println); // 4
        numbers.stream()
                .reduce(Integer::min)
                .ifPresent(System.out::println); // 1
        //forEach用于迭代流中的每个元素，最为常见的就是迭代输出，如：
        list.stream().forEach(System.out::println);
        //count方法用于统计流中元素的个数，比如：
        list.stream().count(); // 9
        //collect方法用于收集流中的元素，并放到不同类型的结果中，比如List、Set或者Map。举个例子：
        List<String> filterList = list.stream()
                .filter(s -> s.startsWith("J")).collect(Collectors.toList());
    }
}
