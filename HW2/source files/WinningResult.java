/*this model holds the properties for the winning result*/
package managedBeans;

public class WinningResult {

private  float mean = 0,standarddeviation = 0;

public void setMean(float meanvalue)
{
mean = meanvalue;
}

public void setStandarddeviation(float standarddeviationvalue)
{
this.standarddeviation = standarddeviationvalue;
}

public float getMean()
{
return mean;
}

public float getStandarddeviation()
{
return standarddeviation;
}
}
