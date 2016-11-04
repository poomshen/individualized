package kosta.vo;

import org.codehaus.jackson.annotate.JsonIgnore;

public class SampleBean implements java.io.Serializable
{
	private static final long	serialVersionUID	= 8005060410080299427L;
	private String id;
	private String name;
	
	public SampleBean( )
	{}

	public String getId( )
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

	@JsonIgnore
	public String getName( )
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}
}