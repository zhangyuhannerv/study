package stack.inverse_polish_calculator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @ClassName RpnCalculator
 * @Description 需求分析：
 * 1.输入一个逆波兰表达式（后缀表达式），使用stack（栈）计算其结果
 * 2.支持小括号和多位数整数，在此类里简化，只支持对整数的计算
 * 3.思路分析
 * 4.代码实现
 * @Author Zhangyuhan
 * @Date 2020/9/29
 * @Version 1.0
 */
public class RpnCalculator {
    public static void main(String[] args) {
        // 完成将一个中缀表达式转成后缀表达式的功能
        // 说明
        // 1. 1+((2+3)*4)-5 => 1 2 3 + 4 * + 5 -
        // 2.因为直接对字符串进行操作，不太方便，因此，先将1+((2+3)*4)-5转成中缀表达式对应的list，即->ArrayList[1,+,(,(,2,+,3,),*,4,),-,5]
        // 3.将得到的中缀表达式对应的list转成后缀表达式对应的list
        // 即：[1,+,(,(,2,+,3,),*,4,),-,5]->[1,2,3,+,4,*,+,5,-]

        // 中缀表达式对应的字符串
        String expression = "1+((22+343)*4)-5";
        // 中缀表达式对应的list
        System.out.println(toInfixExpressionList(expression));// [1, +, (, (, 22, +, 343, ), *, 4, ), -, 5]测试正确
        // 后缀表达式对应的list
        List<String> list = parseSuffixExpressionList(toInfixExpressionList(expression));
        System.out.println(list);//[1,22,343,+,4,*,+,5,-]
        System.out.println("expression=" + calculate(list));// 结果是1456，测试结果正确
        /*

        // 先定义一个逆波兰表达式
        // （30+4）*5-6->30 4 + 5 * 6 -  // 结果是164
        //  4*5-8+60+8/2->4 5 * 8 - 60 + 8 2 / + //结果是76

        // 为了方便，逆波兰表达式中的数字和符号之间用空格隔开了（其实不隔开也可以，多加一些处理即可)
        // String suffixExpression = "30 4 + 5 * 6 -";
        String suffixExpression = "4 5 * 8 - 60 + 8 2 / +";

        // 思路：
        // 1.先将3 4 + 5 * 6 -放到ArrayList中
        // 2.将ArrayList传递给一个方法，遍历ArrayList，配合栈，完成计算（解决扫描字符串过慢的问题）
        List<String> rpnList = getListString(suffixExpression);
        System.out.println("rpnList:" + rpnList);

        int res = calculate(rpnList);
        System.out.printf("计算的结果是%d", res);

         */
    }

    // [1,+,(,(,2,+,3,),*,4,),-,5]->[1,2,3,+,4,*,+,5,-]
    // 方法：将得到的中缀表达式对应的list转成后缀表达式对应的list
    public static List<String> parseSuffixExpressionList(List<String> ls) {
        // 定义两个栈
        Stack<String> s1 = new Stack<>();// 符号栈
        // 说明
        // 因为s2这个栈在整个转换过程中没有pop操作，而且后面还需要逆序输出，因此比较麻烦
        // 所以这里第二个存放中间结果的栈可以弃之不用，直接使用List<String>来代替s2
        // Stack<String> s2 = new Stack<>();// 存放中间结果的栈
        List<String> s2 = new ArrayList<>();

        // 遍历ls
        for (String item : ls) {
            // 如果是一个数字，入s2
            if (item.matches("\\d+")) {// 数字的情况
                s2.add(item);
            } else if (item.equals("(")) {// 左括号的情况
                // 如果是一个左括号"("，也是直接放入符号栈里
                s1.push(item);
            } else if (item.equals(")")) {// 右括号的情况
                // 如果是右括号")"，则依次弹出s1栈顶的运算符，并压入s2，直到遇到左括号为止，并将这一对括号丢弃
                while (!s1.peek().equals("(")) {
                    s2.add(s1.pop());
                }
                s1.pop();// !!!，别忘记，将s1栈里的左括号"("弹出，执行完毕后，一对括号丢弃
            } else {// 运算符的情况
                // 当item的优先级小于等于s1栈顶运算符的优先级时， 将s1栈顶的运算符弹出并加入到s2中，再重复item和s1栈顶运算符优先级的比较
                // 问题：我们缺少一个比较运算符优先级高低的方法
                while (s1.size() != 0 && !"(".equals(s1.peek()) && Operation.getValue(item) <= Operation.getValue(s1.peek())) {
                    s2.add(s1.pop());
                }
                // 循环完成后还需要将item压入s1栈中
                // 或者不用循环的三种情况：s1为空，s1栈顶运算符的优先级小于item的优先级，s1栈顶运算符是左括号"(",也是直接执行下面的代码
                // 注意：在判断运算符优先级的方法里，"("的优先级会是0，所以不满足循环的条件,
                // 但是实际情况中"("的优先级不应该是0，"("的优先级应该是大于"*"和"/"的，而方法中又没有加入对"("的优先级的判断，所以我在while循环的条件上加了一个  !"(".equals(s1.peek())
                s1.push(item);
            }
        }

        // 遍历完中缀表达式后
        // 将s1中剩余的运算符依次弹出并加入到s2中
        while (s1.size() != 0) {
            s2.add(s1.pop());
        }

        return s2;// 注意：因为是存放到list中，因此按顺序输出就是对应的后缀表达式
    }

    // 方法：将中缀表达式转成对应的list
    public static List<String> toInfixExpressionList(String s) {
        // 定义一个list，存放中缀表达式对应的内容
        List<String> list = new ArrayList<>();
        int i = 0;// i是指针，用于遍历中缀表达式字符串
        String str;// str用于多位数的拼接操作
        char c;// 每遍历到一个字符，就放入到c中
        while (i < s.length()) {
            // 如果c是一个非数字，就需要加入list中去
            if ((c = s.charAt(i)) < 48 || (c = s.charAt(i)) > 57) {
                list.add("" + c);
                i++;// i需要后移
            } else {// 如果是一个数，需要考虑多位数的问题
                str = "";// 先将str置成"" '0'[48]->'9'[57]
                while (i < s.length() && ((c = s.charAt(i)) >= 48 && (c = s.charAt(i)) <= 57)) {
                    str += c;// 拼接
                    i++;
                }

                list.add(str);

            }
        }

        return list;// 返回list集合

    }

    // 将一个逆波兰表达式，依次将数据和运算符放入到ArrayList中
    // 逆波兰表达式：30 4 + 5 * 6 -
    public static List<String> getListString(String suffixExpression) {
        // 将suffixExpression进行分割
        String[] split = suffixExpression.split(" ");
        List<String> list = new ArrayList<>();
        for (String str : split) {
            list.add(str);
        }
        return list;
    }

    // 完成对逆波兰表达式的运算
    public static int calculate(List<String> list) {
        // 创建一个栈，此时和之前写的计算器不同的是这里只需要一个栈
        Stack<String> stack = new Stack<>();
        // 遍历list
        for (String item : list) {
            // 这里使用正则表达式来取出数字
            if (item.matches("\\d+")) {// 匹配的是多位数(如1可以入栈，112也可以入栈）
                // 入栈
                stack.push(item);
            } else {
                // 此时是运算符，从栈中pop出两个数，并运算，再入栈
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int res = 0;
                if (item.equals("+")) {
                    res = num1 + num2;
                } else if (item.equals("-")) {
                    res = num1 - num2;
                } else if (item.equals("*")) {
                    res = num1 * num2;
                } else if (item.equals("/")) {
                    res = num1 / num2;
                } else {
                    throw new RuntimeException("运算符有误");
                }
                // 把res入栈
                stack.push(res + "");
            }
        }
        // 最后留在stack中的数据就是运算结果
        return Integer.parseInt(stack.pop());
    }
}

// 编写一个类Operation，该类可以返回一个运算符对应的优先级
class Operation {
    private static final int Add = 1;
    private static final int SUB = 1;
    private static final int MUL = 2;
    private static final int DIV = 2;

    // 写一个方法，返回对应的优先级数字
    public static int getValue(String operation) {
        int result = 0;
        switch (operation) {
            case "+":
                result = Add;
                break;
            case "-":
                result = SUB;
                break;
            case "*":
                result = MUL;
                break;
            case "/":
                result = DIV;
                break;
            default:
                System.out.println("不存在该运算符");
                break;
        }
        return result;
    }
}












