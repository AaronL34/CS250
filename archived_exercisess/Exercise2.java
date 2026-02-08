package cs250.exercises;

public class Exercise2 {
    public static void main(String[] args) {
        String a = args[0];
        String b = args[1];
        String c = args[2];

        int aNum = Integer.parseInt(a);
        float bNum = Float.parseFloat(b);
        long cNum = Long.parseLong(c);

        System.out.println(a);
        System.out.println(b);
        System.out.println(c);

        System.out.println(aNum + bNum + cNum);
        System.out.println(aNum - bNum - cNum);
        System.out.println(aNum * bNum * cNum);
        System.out.println(aNum / bNum / cNum);

        System.out.println(Math.pow(bNum, aNum));
        System.out.println(cNum % aNum);
        System.out.println(cNum / (-bNum));

    }
    
}
