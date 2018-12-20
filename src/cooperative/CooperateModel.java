/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cooperative;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Vector;

/**
 *
 * @author admin
 */
public class CooperateModel 
{
    //int C=10;
    int q=10; // no of sequence
    Details dt=new Details();
    
    CooperateModel()
    {
        
    }
    
    public void cooperate(int episode,String mtd,int iter,int st,int act)
    {
        try
        {
            int st1=dt.states[0].size();
            
            switch(mtd)
            {
                case "group":
                    if(episode%q==0)
                    {
                        get_Ereward();
                        //getPolicy(st,act);
                        getPolicy(iter,st);
                    }
                break;
                
                case "dynamic":   
                    get_Ereward();
                    double r=0;
                    for(int i=0;i<dt.Agent;i++)
                    {
                        r=r+dt.Q[i][act];
                    }
                    dt.re[iter]=r;
                    dt.Q[iter][act]=r;
                    //getPolicy(st,act);
                    getPolicy(iter,st);
                break;
                case "goal-driven":
                    if(st==dt.StateGoal)
                    {
                        get_Ereward();
                        double r1=0;
                        for(int i=0;i<dt.Agent;i++)
                        {
                            r1=r1+dt.Q[i][act];
                        }
                        dt.re[iter]=r1;
                        dt.Q[iter][act]=r1;
                        //getPolicy(st,act);
                        getPolicy(iter,st);
                    }
                break;
                case "expert_agent":
                    
                    get_ExpertAgent(iter);
                    if(dt.ei[iter]==dt.ea)
                    {
                        get_Ereward();
                        double r1=0;
                        for(int i=0;i<dt.Agent;i++)
                        {
                            r1=r1+dt.Q[i][act];
                        }
                        dt.re[iter]=r1;
                        dt.Q[iter][act]=r1;
                        //getPolicy(st,act);
                        getPolicy(iter,st);
                    }
                break;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void get_Ereward()
    {
        try
        {
            for(int i=0;i<dt.Agent;i++)
            {
                for(int j=0;j<dt.State;j++)
                {
                    get_Expertness();
                    double ej=0;
                    for(int k=0;k<dt.Agent;k++)
                    {
                        ej=ej+dt.ei[k];
                    }
                    dt.ri[i]=dt.R[i][j]*(dt.ei[i]/ej);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void get_ExpertAgent(int a1)
    {
        try
        {
            for(int i=0;i<dt.Agent;i++)
            {
                for(int j=0;j<dt.State;j++)
                {
                    get_Expertness();
                    if(dt.ei[a1]>dt.ei[i])
                        dt.ea=dt.ei[a1];
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void get_Expertness()
    {
        for(int i=0;i<dt.Agent;i++)
        {            
            for(int j=0;j<dt.State;j++)
            {
                dt.ei[i]=dt.ri[i];
            }
        }
    }
     public void getPolicy(int s1,int a1)
    {
        try
        {
            for(int i=0;i<dt.Agent;i++)
            {
                for(int j=0;j<dt.State;j++)
                {
                    if(Cost1(j,i)<=Cost2(j))
                    {
                        dt.UAP[s1][a1]=dt.Q[s1][a1];
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }    
    
    public double Cost1(int st1,int age)
    {
        double ct=0;
        try
        {
            ct=dt.Q[age][st1];           
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return ct;
    }
    
    public double Cost2(int st1)
    {
        double ct=0;
        try
        {
            for(int i=0;i<dt.Agent;i++)
                ct=ct+dt.Q[i][st1];
            ct=ct/dt.Agent;            
           
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return ct;
    }
   
    
}
