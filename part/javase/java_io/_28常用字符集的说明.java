package java_io;

/**
 * @ClassName _28常用字符集的说明
 * ASCII:美国标准信息交换码。用一个字节的7位表示
 * <p>
 * ISO8859-1：拉丁码表，用一个字节的8位表示
 * <p>
 * GB2312:中国的中文编码表，最多两个字节编码所有字符，兼容ASCII码,中文两个字节表示，abcd等英文字符符号还是一个字节表示
 * GBK：中国的中文编码表的升级。融合了更多的中文字符符号，最多两个字节编码所有字符，兼容ASCII码,中文两个字节表示，abcd等英文字符符号还是一个字节表示
 * GBK和GB2312的最高位如果是0代便是一个字节的编码。如果是1，证明是两个字节的编码
 * <p>
 * Unicode:国际的标准码，融合了目前人类使用的所有字符。为每个字符分配唯一的字符码。所有的文字都用两个字节来表示
 * Unicode的三个问题：
 * 1.英文字母一个字节就够用了。多出来的一个字节是浪费
 * 2.两个字节的话怎么区分是两个ASCII码还是一个Unicode码呢
 * 3.如果和GBK的区分方式一样。利用最高位是0或者1来区分。那么组合方式就从2^16个变成了2^15个。少了很多。导致不够用了
 * 综上：很长一段时间。Unicode都无法推广
 * <p>
 * UTF(USC Transfer Format)传输标准
 * UTF-8是每次8个位传输数据(变长的编码方式，为1到4个字节)
 * 补充：超过基本多语言范围的的字符在标准utf-8编码中为4个字节。在修正后的utf-8编码中将需要6个字节
 * UTF-16是每次16个位传输数据
 * UTF是为传输而设计的编码标准
 * <p>
 * ANSI(一种编码标准):通常代指指操作平台的默认编码：英文默认是ISO-8859-1,中文是GBK，日文为JIS
 * <p>
 * Unicode字符集，只是定义了字符的集合和唯一编号
 * Unicode编码则是对UTF-8/USC-2/UTF-16/UTF-32等具体编码方案的统称而已，并不是具体的编码方案
 * @Author Zhangyuhan
 * @Date 2021/11/12
 * @Version 1.0
 */
public class _28常用字符集的说明 {
}
