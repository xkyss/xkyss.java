package com.xkyss.test.easy.query;

import com.easy.query.api4j.client.DefaultEasyQuery;
import com.easy.query.api4j.client.EasyQuery;
import com.easy.query.api4j.insert.EntityInsertable;
import com.easy.query.api4j.select.Queryable;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


public class MySqlTest {

    EasyQuery easyQuery = new DefaultEasyQuery(EasyQueryBootstrapper.defaultBuilderConfiguration()
        .setDefaultDataSource(new EmptyDataSource())
        .useDatabaseConfigure(new MySQLDatabaseConfiguration())
        .build());


    @Test
    public void test_query() {
        String sql = easyQuery.queryable(Book.class)
            .select(o -> o.columnAll()
                .sqlNativeSegment("rank() over(order by {0} desc) as rank1", it -> it.expression(Book::getPrice))
                .sqlNativeSegment("rank() over(partition by {0} order by {1} desc) as rank2", it -> it
                    .expression(Book::getStoreId)
                    .expression(Book::getPrice)
                )
            ).toSQL();

        System.out.println(sql);
    }

    @Test
    public void test_query_2() {
        Queryable<Book> queryable = easyQuery.queryable(Book.class)
            .where(o -> o.eq(Book::getId, "1"));
        ToSQLContext context = DefaultToSQLContext.defaultToSQLContext(
            queryable.getSQLEntityExpressionBuilder().getExpressionContext().getTableContext());

        String sql = queryable.toSQL(context);
        System.out.println(sql);
        String params = EasySQLUtil.sqlParameterToString(context.getParameters());
        System.out.println(params);
    }

    @Test
    public void test_insert_1() {
        Book book = createBook("1");
        Book book2 = createBook("2");
        String sql = easyQuery.insertable(book).toSQL(book2);
        System.out.println(sql);
    }

    @Test
    public void test_insert_2() {
        List<Book> books = Arrays.asList(createBook("1"), createBook("2"));
        EntityInsertable<Book> insertable = easyQuery.insertable(books);
        ToSQLContext context = DefaultToSQLContext.defaultToSQLContext(
            insertable.getEntityInsertExpressionBuilder().getExpressionContext().getTableContext());

        String sql = insertable.toSQL(createBook("3"), context);
        System.out.println(sql);
    }

    private static Book createBook(String id) {
        Book book = new Book();
        book.setId(id);
        book.setName("n" + id);
        book.setEdition("e" + id);
        // book.setPrice("p" + id);
        book.setStoreId("s" + id);
        return book;
    }
}
