public class OpCode {
    public static final byte EXIT = 0;
    public static final byte ADD = 1;
    public static final byte SUB = 2;
    public static final byte MUL = 3;
    public static final byte DIV = 4;
    
    public static byte getOpCodeForString(String s){
        byte op;
        if(s.equals("+")){
            op = OpCode.ADD;
        }else if(s.equals("-")){
            op = OpCode.SUB;
        }else if(s.equals("*")){
            op = OpCode.MUL;
        }else if(s.equals("/")){
            op = OpCode.DIV;
        } else{
            op = OpCode.EXIT;
        }
        return op;
    }
}
