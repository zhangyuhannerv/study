package annotation;

/**
 * @ClassName Worker
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/18
 * @Version 1.0
 */

@MyAnno(value = 12, showPersonEnum = PersonENnum.P1, showAnno = @MyAnno2, showStrs = {"abc", "bbb"})
@MyAnno3
public class Worker {
    @MyAnno3
    private String name = "aaa";

    @MyAnno3
    public void show() {

    }
}
