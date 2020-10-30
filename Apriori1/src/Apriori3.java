import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;
public class Apriori3
{
private int min_Supp;
private int min_Confi;
static List<Apriori2> rl = new ArrayList<Apriori2>();
public ArrayList<HashSet<String>> tran_Val;
public ArrayList<ArrayList<ItemSet>> freq_Sets;
public Apriori3(ArrayList<HashSet<String>> trans_Val, int min_Support,int min_Conf)
{
this.min_Supp = min_Support;
this.min_Confi=min_Conf;
this.tran_Val = trans_Val;
this.freq_Sets = new ArrayList<ArrayList<ItemSet>>();
}
public void flow()
{
freq_Sets.add(new ArrayList<ItemSet>());
String[] Items;
Map<String, Integer> wc = new HashMap<String, Integer>();
for (int i = 0; i < tran_Val.size(); i++)
{
Items = tran_Val.get(i).toArray(new
String[tran_Val.get(i).size()]);
for(String item: Items)
{
Integer count = wc.get(item);
wc.put(item, (count==null) ? 1 : count+1);
}
}
HashSet<String> ds;
Iterator<Entry<String, Integer>> it = wc.entrySet().iterator();
while (it.hasNext())
{
Map.Entry<String, Integer> pairs = (Map.Entry<String,Integer>)it.next();
ds = new HashSet<String>();
ds.add(pairs.getKey());
freq_Sets.get(0).add(new ItemSet(ds,pairs.getValue().intValue()));
}
MinimumSupport(0);
System.out.println("\nItems Set:");
for (int j = 0; j < freq_Sets.get(0).size(); j++)
{
System.out.println((freq_Sets.get(0)).get(j).getItem()+ " - "+(freq_Sets.get(0)).get(j).getSC());
}
int iteration = 0;
while (freq_Sets.get(iteration).size() != 0)
{
freq_Sets.add(new ArrayList<ItemSet>());
Combine(iteration);
iteration++;
popSC(iteration);
MinimumSupport(iteration);
System.out.println("\nItemSet "+(iteration+1) +":");
for (int i = 0; i < freq_Sets.get(iteration).size(); i++)
{
System.out.print(freq_Sets.get(iteration).get(i).getItem()+" - ");
System.out.println(freq_Sets.get(iteration).get(i).getSC());
}
}
System.out.println("\nFrequent Items:");
for (int i = 0; i < this.freq_Sets.size(); i++)
{
ArrayList<ItemSet> set = this.freq_Sets.get(i);
for(int j=0;j<set.size();j++)
{
ItemSet its =set.get(j);
System.out.println(its.getItem() + " - "+
its.getSC());
}
}
ArrayList<ItemSet> nextIt = new ArrayList<ItemSet>();
for(int i=1;i<this.freq_Sets.size();i++)
{
ArrayList<ItemSet> temp = new ArrayList<ItemSet>();
temp=this.freq_Sets.get(i);
for(int j=0;j<temp.size();j++)
{
ItemSet val = temp.get(j);
nextIt.add(val);
}
}
genNext(nextIt);
}
private void Combine(int iteration) {
HashSet<String> itemSet= new HashSet<String>();
ArrayList<HashSet<String>> list= new ArrayList<HashSet<String>>();
boolean present = false;
for (int i = 0; i < freq_Sets.get(iteration).size(); i++)
{
for (int j = i + 1; j < freq_Sets.get(iteration).size(); j++)
{
itemSet = new HashSet<String>();
int count=0;
HashSet<String>
rep=freq_Sets.get(iteration).get(j).getItem();
for(String dup : rep)
{
if(freq_Sets.get(iteration).get(i).getItem().contains(dup)){
count++;
}
}
if(count==iteration)
{
itemSet.addAll(freq_Sets.get(iteration).get(i).getItem());
itemSet.addAll(freq_Sets.get(iteration).get(j).getItem());
list.add(itemSet);
System.out.print(freq_Sets.get(iteration).get(i).getItem());
System.out.print(freq_Sets.get(iteration).get(j).getItem());
}
}
}
System.out.print("\n");
for (HashSet<String> val : list)
{
for (int k = 0; k < freq_Sets.get(iteration + 1).size() && !present;k++)
{
if (freq_Sets.get(iteration +
1).get(k).getItem().equals(val))
present = true;
}
if(!present)
{
freq_Sets.get(iteration + 1).add(new ItemSet(val, 0));
}
else{
present = false;
}
}
}
private void popSC(int level)
{
for (int i = 0; i < freq_Sets.get(level).size(); i++)
{
for (int j = 0; j < tran_Val.size(); j++)
{
if(tran_Val.get(j).containsAll(freq_Sets.get(level).get(i).getItem()))
freq_Sets.get(level).get(i).getSC(freq_Sets.get(level).get(i).getSC() + 1);
}
}
}
private void MinimumSupport(int level)
{
for (int i = 0; i < freq_Sets.get(level).size(); i++)
{
if(((freq_Sets.get(level).get(i).getSC()/(double)tran_Val.size())*100)<
min_Supp)
{
freq_Sets.get(level).remove(i);
i--;
}
}
}
private void genNext(ArrayList<ItemSet> temp)
{
List<Apriori2> BeforePruning = new ArrayList<Apriori2>();
for(ItemSet itset : temp)
{
Set<String> itemSet=itset.getItem();
int len=itemSet.size();
int x=(int) Math.pow(2, len);
String[] item_Array = itemSet.toArray(new
String[itemSet.size()]);
ArrayList<TreeSet<String>> nes=new ArrayList<TreeSet<String>>();
for (int i=1; i<x-1; i++)
{
int con = i;
int position = 0;
TreeSet<String> ele = new TreeSet<String>();
while (con > 0)
{
if ((con & 1) == 1)
{
ele.add(item_Array[position]);
}
con >>= 1;
position++;
}
nes.add(ele);
}
for(Set<String> s :nes)//
{
Set<String> Diff = new HashSet<String>(itemSet);
Diff.addAll(s);
Set<String> tmp = new HashSet<String>(itemSet);
tmp.retainAll(s);
Diff.removeAll(tmp);
Apriori2 arule = new Apriori2(tran_Val);
arule.setls(tmp);
arule.setrs(Diff);
arule.setlsSupp(itset.getSC());
arule.setrsSupp(itset.getSC());
BeforePruning.add(arule);
}
}
for (int i = 0; i < BeforePruning.size(); i++)
{
Apriori2 currentRule = BeforePruning.get(i);
if (currentRule.getls().containsAll(currentRule.getrs()))
{
BeforePruning.remove(currentRule);
continue;
}
if (currentRule.confCal() < (min_Confi/100d))
{
continue;
}
rl.add(currentRule);
}
System.out.println("\nFrequent Itemset and Support values computed:");
for(int i=0;i<this.freq_Sets.size();i++)
{
ArrayList<ItemSet> al = new ArrayList<ItemSet>();
al=this.freq_Sets.get(i);
for(int j=0;j<al.size();j++)
{
ItemSet val = al.get(j);
Set<String> key = val.getItem();
Integer v = val.getSC();
double support = (v / (double) tran_Val.size()) *100.0; 
System.out.println(Arrays.toString(key.toArray()) + " - "+ new DecimalFormat().format(support) + "%");
}
}
System.out.println("\nFinal output:\n");
for (int i = 0; i < rl.size(); i++)
{
System.out.println(rl.get(i));
}
}
public ArrayList<HashSet<String>> gettrans_Val() {
return tran_Val;
}
public void settrans_Val(ArrayList<HashSet<String>> trans_Val) {
this.tran_Val = trans_Val;
}
public ArrayList<ArrayList<ItemSet>> getfreq_Sets() {
return freq_Sets;
}
public void setfreq_Sets(ArrayList<ArrayList<ItemSet>> freq_Sets) {
this.freq_Sets = freq_Sets;
}
public int getmin_Support() {
return min_Supp;
}
public void setmin_Support(int min_Support) {
this.min_Supp = min_Support;
}
public int getmin_Conf() {
return min_Confi;
}
public void setmin_Conf(int min_Conf) {
this.min_Confi = min_Conf;
}
}
class ItemSet
{
private HashSet<String> item;
private int suppCount;
public ItemSet(HashSet<String> item, int count) {
this.item = item;
this.suppCount = count;
}
public HashSet<String> getItem() {
return item;
}
public void setItem(HashSet<String> item) {
this.item = item;
}
public int getSC() {
return suppCount;
}
public void getSC(int suppCount) {
this.suppCount = suppCount;
}
}