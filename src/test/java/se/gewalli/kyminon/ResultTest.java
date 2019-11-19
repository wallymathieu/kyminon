package se.gewalli.kyminon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ResultTest {
    @Test
    public void testThatOkCanFolded() {
        Result<String, String> t = Result.ok("ok");
        assertEquals("ok", t.fold((v) -> v, (v) -> v));
    }

    @Test
    public void testThatErrorCanFolded() {
        Result<String, String> t = Result.error("error");
        assertEquals("error", t.fold((v) -> v, (v) -> v));
    }

    @Test
    public void testThatMapOnOkWorksAsExpected() {
        Result<Integer, String> t = Result.ok(1);
        assertEquals(Result.ok("1"), t.map((v) -> v.toString()));
    }

    @Test
    public void testThatMapOnErrorWorksAsExpected() {
        Result<Integer, String> t = Result.error("error");
        assertEquals(Result.error("error"), t.map((v) -> v.toString()));
    }
}