package stack.calculator;

import java.util.Scanner;

/**
 * @ClassName Calculator
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/28
 * @Version 1.0
 */
public class Calculator {
    public static void main(String[] args) {
        // 根据思路，完成表达式的运算
        Scanner input = new Scanner(System.in);
        String expression = input.next();
        // 先创建两个栈，一个是数栈，一个是符号栈
        ArrayStack2 numStack = new ArrayStack2(50);
        ArrayStack2 operStack = new ArrayStack2(50);
        // 定义需要的相关变量
        int index = 0;// 用于扫描
        char ch = ' ';// 将每次扫描得到的char保存到ch
        int num1 = 0;// 数字栈中先出的数字
        int num2 = 0;// 数字栈中后弹出的数字
        int oper = 0;// 符号栈中弹出的运算符
        int res = 0;// 栈中弹出的两个数字计算后的结果
        String keepNum = "";// 用于拼接多位数
        // 开始用while语句循环的扫描expression
        while (true) {
            // 依次得到expression里面的每个字符
            ch = expression.substring(index, index + 1).charAt(0);
            // 判断ch是什么，然后做相应的处理
            if (operStack.isOper(ch)) {// 如果是运算符
                // 判断当前的符号栈是否为空
                if (!operStack.isEmpty()) {
                    // 如果符号栈有操作符，就进行比较，如果当前的操作符的优先级小于或者等于栈中的操作符
                    // 就需要从数栈中pop出两个数字，再从符号栈中pop出一个符号，进行运算，将得到的结果，推入数栈
                    // 然后将当前的操作符再和符号栈顶的操作符进行比较，重复上述的过程，直到优先级大于栈顶的操作符，或栈为空，运算符才能入栈
                    boolean isSmaller = true;

                    while (isSmaller) {
                        if (operStack.isEmpty()) {
                            isSmaller = false;
                            operStack.push(ch);
                        } else if (operStack.priority(ch) <= operStack.priority(operStack.peek())) {
                            num1 = numStack.pop();
                            num2 = numStack.pop();
                            oper = operStack.pop();
                            res = numStack.cal(num1, num2, oper);
                            // 把运算的结果入数栈
                            numStack.push(res);
                            // 注意这里算完之后不能直接将操作符入栈，而是要继续和栈里的操作符进行比较，直到优先级大于栈顶的操作符，或栈为空，运算符才能入栈
                            // 如果直接放入的话，反例:2-3*4+5=-15
                            // operStack.push(ch);
                        } else {
                            // 如果当前的操作符的优先级大于栈中的操作符，就直接入符号栈
                            isSmaller = false;
                            operStack.push(ch);
                        }
                    }

                } else {
                    // 如果为空，直接入符号栈
                    operStack.push(ch);// 1+3
                }
            } else {
                // 如果是数字，则直接入数字栈
                // numStack.push(ch - 48);// '1'->1

                // 分析思路
                // 1.当处理多位数时，不能发现是一个数就立即入数栈，因为他可能是一个多位数
                // 2.在处理数字时，需要向expression表达式的index后再看一位，如果是数字就继续扫描，如果是符号，才将数字入栈
                // 3.因此我们需要定义一个变量（字符串）用于拼接

                // 处理多位数
                keepNum += ch;
                // 如果ch已经是expression的最后一位，就直接入栈
                if (index == expression.length() - 1) {
                    numStack.push(Integer.parseInt(keepNum));
                } else {
                    // 判断下一个字符是不是数字，如果是数字，就继续扫描
                    // 如果是运算符，则入栈
                    // 注意是看后面一位，不是index++
                    if (operStack.isOper(expression.substring(index + 1, index + 2).charAt(0))) {
                        // 如果后一位是操作符，则入栈
                        numStack.push(Integer.parseInt(keepNum));
                        // 一定要将keepNum清空
                        keepNum = "";
                    }
                }
            }
            // 让index+1，并判断是否扫描到expression最后
            index++;
            if (index > expression.length() - 1) {// 这里index是'>'
                break;
            }
        }

        System.out.println("--------------------");

        // 当表达式扫描完毕，就顺序的从数栈和符号栈中pop出相应的数字和符号，并运行
        while (true) {
            // 如果符号栈为空，则计算到最后的结果了，此时数栈中只有一个数字
            if (operStack.isEmpty()) {
                break;
            }

            num1 = numStack.pop();
            num2 = numStack.pop();
            oper = operStack.pop();
            res = numStack.cal(num1, num2, oper);
            // 把运算的结果入数栈
            numStack.push(res);
        }

        // 将数栈最后的数字pop出来，就是结果
        int res2 = numStack.pop();
        System.out.printf("表达式%s=%d", expression, res2);


    }
}
