import java.text.DecimalFormat;
import java.util.*;
public class Apriori2              
{
int p,q;
double conf;
int support;
Set<String> ls;
Set<String> rs;
ArrayList<HashSet<String>> trans = new ArrayList<HashSet<String>>();
Apriori2(ArrayList<HashSet<String>> tl)
{
trans = tl;
ls = new HashSet<String>();
rs = new HashSet<String>();
}
public String toString() 
// displaying rules 
{
return Arrays.toString(ls.toArray()) + " => "+ Arrays.toString(rs.toArray()) + " (Support: "+ new DecimalFormat().format((supp_Count(union(ls, rs)) / (double)trans.size()) * 100) + " % , confidence: " + new DecimalFormat().format(conf* 100)+ "%)";
}
public double confCal()    // Calculationg confidence
{
conf = supp_Count(union(ls, rs));
support = (int) conf;
conf /= supp_Count(ls);
return conf;
}
private int supp_Count(Set<String> set)
{
int ct = 0;
for (int i = 0; i < trans.size(); i++)
{
if (trans.get(i).containsAll(set)) {
ct++;
}
}
return ct;
}
public <T> Set<T> union(Set<T> setA, Set<T> setB)
{
Set<T> tmp = new HashSet<T>(setA);
tmp.addAll(setB);
return tmp;
}

public double getconf()
{
return conf;
}
public void setconf(double conf)
{
this.conf = conf;
}
public int getSupport()
{
return support;
}
public void setSupport(int support)
{
this.support = support;
}
public Set<String> getls()
{
return ls;
}
public void setls(Set<String> ls)
{
this.ls = ls;
}
public Set<String> getrs()
{
return rs;
}
public void setrs(Set<String> rs)
{
this.rs = rs;
}
public void settranss(ArrayList<HashSet<String>>transList)
{
this.trans = trans;
}
public ArrayList<HashSet<String>> gettranss()
{
return trans;
}
public int getlsSupp()
{
return p;
}
public void setlsSupp(int lsSupp)
{
this.p = p;
}
public int getrsSupp()
{
return q;
}
public void setrsSupp(int rsSupp)
{
this.q = q;
}
}
