package com.lordofthejars.nosqlunit.demo.redis;

import static com.lordofthejars.nosqlunit.redis.ManagedRedis.ManagedRedisRuleBuilder.newManagedRedisRule;
import static com.lordofthejars.nosqlunit.redis.RedisRule.RedisRuleBuilder.newRedisRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import redis.clients.jedis.Jedis;

import com.lordofthejars.nosqlunit.annotation.ShouldMatchDataSet;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.demo.model.Book;
import com.lordofthejars.nosqlunit.redis.ManagedRedis;
import com.lordofthejars.nosqlunit.redis.RedisRule;

public class WhenANewBookIsCreated {

	static {
		System.setProperty("REDIS_HOME", "/opt/redis-2.4.16");
	}

	@ClassRule
	public static ManagedRedis managedRedis = newManagedRedisRule().build();

	@Rule
	public RedisRule redisRule = newRedisRule().defaultManagedRedis();
	
	@Test
	@UsingDataSet(locations="book.json", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	@ShouldMatchDataSet(location="expected_book.json")
	public void book_should_be_inserted_into_repository() {
		
		BookManager bookManager = new BookManager(new Jedis("localhost"));
		bookManager.insertBook(new Book("The Lord Of The Rings", 1299));
		
	}
	
}
