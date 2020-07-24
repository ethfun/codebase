package com.inglc.codebase.vavr;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Try;
import io.vavr.control.Try.Failure;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;

/**
 * @author liuchen
 * @date 2020/6/22
 */
@Service
public class VavrService {

	public static void main(String[] args) {
		Try<Integer> try1 = divide(3,0);
		if (try1 instanceof Try.Failure) {
			System.out.println("Divide exception");
		} else {
			System.out.println("Divide SUCCESS");
		}

	}

	static Try<Integer> divide(Integer dividend, Integer divisor) {
		return Try.of(() -> dividend / divisor);
	}

	public void vavrDataType(){
		Tuple2<String, Integer> java8 = Tuple.of("Java", 8);

		int k = 1;
		String s = Match(k).of(
				Case($(1), "one"),
				Case($(2), "two"),
				Case($(), "?")
		);


		IntStream.of(1, 2, 3).sum();
		Arrays.asList(1, 2, 3).stream().reduce((i, j) -> i + j);


	}

}
