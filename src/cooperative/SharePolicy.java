/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cooperative;
import java.util.Random;
import java.util.ArrayList;
import java.text.DecimalFormat;
/**
 *
 * @author admin
 */
public class SharePolicy 
{
     Details dt=new Details();
    
    SharePolicy()
    {
        
    }
    
    public void share()
    {
        try
        {
            DecimalFormat df=new DecimalFormat("#.###");
            CooperateModel cm=new CooperateModel(); 
            //int st1=dt.states[0].size();
                
            for(int mm=0;mm<dt.technique.length;mm++)
            {
                String mtd=dt.technique[mm];
                dt.Q=new double[dt.Agent][dt.State];
                dt.UAP=new double[dt.Agent][dt.State];
            
                // Initialize Q and GAP
                for(int i=0;i<dt.Agent;i++)
                {
                    for(int j=0;j<dt.State;j++)
                    {                
                        dt.Q[i][j]=0;
                        dt.UAP[i][j]=0;                   
                    }
                }
                Random rn=new Random();
                int Step=0;
                for(int i=0;i<dt.Agent;i++)
                {
                    int st1=rn.nextInt(dt.State);
                    while(st1!=dt.StateGoal)
                    {
                        Step=Step+1;
                        int index=rn.nextInt(dt.State);
                              
                        int next=index;
                        int act=next;
                    
                        double q = dt.Q[i][st1];
                        double r = dt.R[i][st1];
                        double value = q + dt.alpha * (r + dt.gamma - q);
                        dt.Q[i][st1]=value;
                        
                        //int m1=rn.nextInt(dt.method.length);
                        
                        if(!mtd.equals("Simple"))
                            cm.cooperate(Step, mtd, i, st1, act);
                        
                        st1=next;
                    }
                    System.out.println("steps "+Step);
                    
                    String res="";
                    for(int j=0;j<dt.State;j++)
                    {
                        //System.out.print(df.format(dt.Q[i][j])+" ");                   
                        res=res+df.format(dt.Q[i][j])+"#";
                    }
                    dt.result[mm][i]=res;
                    dt.text=dt.text+mtd+" = Agent-"+(i+1)+" = "+res.replace("#", " , ")+"\n";
                    System.out.println("=============================="+mtd+" : "+(i+1)+" = "+res);
                } // Agent
            }// method
            
            dt.text=dt.text+"========================================\n\n";
           /* for(int i=0;i<dt.Agent;i++)
            {
                Graph gr=new Graph(i);
                gr.display();
            }*/
            
                
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }            
    }
}
