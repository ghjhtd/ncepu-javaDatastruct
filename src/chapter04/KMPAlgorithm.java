package chapter04;

public class KMPAlgorithm {
       public static void main(String[] args) {
            String T="aaaabbbcccdddeef";
            String P="abb";
            System.out.println("使用朴素匹配算法：");
            System.out.printf("%s在%s中的位置是%d\n",P,T,nativeCompare(T,P,0));
            System.out.println("使用KMP匹配算法：");
            System.out.printf("%s在%s中的位置是%d\n",P,T,kmpCompare(T,P,0));
      }
       static int nativeCompare(String T,String P,int pos){
          int n=T.length();int m=P.length();
           for(int i=pos;i<n-m;i++){
               int k=i, j;
               for(j=0;j<m&&P.charAt(j)==T.charAt(k);j++){
                        k++;               
               }
               if(j==m) return i;
           }
           return -1;
       }
       static int kmpCompare(String T,String P,int pos){
          int  n=T.length(),m=P.length();
          int i=pos,j=0;
          int[] nextval=getnextval(P);
          while(i<n){
              if(j==-1||T.charAt(i)==P.charAt(j)){
                  j++;i++;
              }else j=nextval[j];
              if(j==m) return i-j;
          }
           return -1;
       }

    private static int[] getnextval(String P) {
        int  m=P.length();
         int[] nextval=new int[m];
         nextval[0]=-1;
         nextval[1]=0;
         int k=0,j=1;
         while(j<m-1){
            if(k==-1||P.charAt(j)==P.charAt(k)){
               j++;k++;
               if(P.charAt(j)==P.charAt(k))
                    nextval[j]=nextval[k];
               else
                   nextval[j]=k;
            }else
               k=nextval[k];
         }
         return nextval;
    }
}
