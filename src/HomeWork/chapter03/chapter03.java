package HomeWork.chapter03;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author 龚皓靖
 * @班级 信管1901
 * @学号 120191140216
 */
public class chapter03 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Stack stack;
        int num , which;
        while(true){
            System.out.println("请您输入整数:");
            num = sc.nextInt();
            System.out.println("请您输入需要转换的进制:");
            which = sc.nextInt();
            stack = convert(num,which);
            while (!stack.empty()) {
                System.out.print(stack.pop());
            }
            System.out.println();
        }
    }

    /**
     * @author 龚皓靖
     * @param num 整数
     * @param which 进制转换类型
     * @return 保存数据的栈
     */
    public static Stack convert(int num,int which){
        int temp;
        Stack stack = new Stack();
        if ((num / which) == 0){
            stack.push(num);
        }
        else {
            while ((num / which) != 0){
                temp = num % which;
                num = num / which;
                stack.push(temp);
            }
            stack.push(num);
        }
        return stack;
    }
}
