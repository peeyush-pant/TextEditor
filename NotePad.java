
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

class NotePad extends WindowAdapter implements ActionListener
{
Frame f;
TextArea t;
MenuBar mb;
Menu m1,m2;


Frame f2;
Label l;
TextField t2;


Frame f3;
Button b4,b5,b6,b7;
Label l3,l2;
TextField t3,t4;

MenuItem mi1,mi2,mi3,mi4,mi5,mi6,mi7;
int count,diff;
boolean saved,start,isFind,toExit,toOpen,close,dont,dig,isReplace,ifold;
String str2,savedString="",savedString2,fileName="NewDocument.txt",savedDirec="",savedFile="";
Frame fadd,fadd2,fadd3;


public NotePad()
{
	f=new Frame();
	f.setTitle("NotePad  "+fileName);
	f.setSize(700,700);
	f.setLayout(new BorderLayout());
	
	f.addWindowListener(this);

	mb =new MenuBar();

	m1=new Menu("File");
	m2=new Menu("Edit");
	
	mi1=new MenuItem("New");
	mi2=new MenuItem("Open");
	mi3=new MenuItem("Save");
	mi4=new MenuItem("Save As");
	mi5=new MenuItem("Exit");
	mi6=new MenuItem("Find.");
	mi7=new MenuItem("Find & Replace");

	mi1.addActionListener(this);
	mi2.addActionListener(this);
	mi3.addActionListener(this);
	mi4.addActionListener(this);
	
	mi5.addActionListener(this);
	mi6.addActionListener(this);
	mi7.addActionListener(this);
	
	m1.add(mi1);
	m1.add(mi2);
	m1.add(mi3);
	m1.add(mi4);
	m1.addSeparator();
	m1.add(mi5);
	m2.add(mi6);
	m2.add(mi7);

	mb.add(m1);
	mb.add(m2);

	f.setMenuBar(mb);

	t=new TextArea("");
	f.add(t);
	f.setVisible(true);
	


}
public void actionPerformed(ActionEvent e)
{
checkSaved();
changeName();
switch(e.getActionCommand())
{
case "New":New();
		break;

case "Open":open();
		break;
case "Save":save();
		break;
case "Save As":
		saveAs();
		break;
		
case "Exit":
		Exit1(f);
		break;
case "Find.":
		
		find();
		break;
case "Find":
		count=0;
		str2=t2.getText();
		savedString2=str2;
		if(str2.length()==0)
		{
		}
		else
		{
		String str;
		str=t.getText();
		
		
		Pattern p=Pattern.compile(str2);
		Matcher m=p.matcher(str);
		if(m.find())
		{
		int j=0;
		for(int i=0;i<m.start();i++)
		if(str.charAt(i)=='\r')
		j++;
		f.toFront();
		t.select(m.start()-j,m.end()-j);
		}
		count=1;
		}
		break;
case "Find next":
		
		str2=t2.getText();
		if(str2.length()==0)
		{
		}
		else
		{
		if(start==true)
		count+=1;
		if(!(str2.equals(savedString2)))
		{
		count=0;
		}
		String str;
		str=t.getText();
		Pattern p=Pattern.compile(str2);
		Matcher m=p.matcher(str);
		int i=0;
		while(i<count)
		{
		m.find();
		i++;
		}
		if(m.find())
		{
		int j=0;
		for(i=0;i<m.start();i++)
		if(str.charAt(i)=='\r')
		j++;
		f.toFront();
		t.select(m.start()-j,m.end()-j);
		savedString2=str2;
		count+=1;
		}
		start=false;
		}
		break;

case "Find previous":
		
		str2=t2.getText();
		if(str2.length()==0)
		{
		}
		else
		{
		if((!(count-1==0))&&start==false)
		count-=1;
		if(!(str2.equals(savedString2)))
		{
		count=0;
		}
		String str;
		str=t.getText();
		Pattern p=Pattern.compile(str2);
		Matcher m=p.matcher(str);
		int i=0;
		while(i<count-1)
		{
		m.find();
		i++;
		}
		if(m.find())
		{
		int j=0;
		for(i=0;i<m.start();i++)
		if(str.charAt(i)=='\r')
		j++;
		f.toFront();
		t.select(m.start()-j,m.end()-j);
		if(count>0)
		count-=1;
		savedString2=str2;
		}
		start=true;
		}
		break;
case "Find & Replace":replace();
		break;

case "Replace All":
		str2=t3.getText();
		
		if(str2.length()==0)
		{
		}
		else
		{
		String str;
		str=t.getText();
		
		Pattern p=Pattern.compile(str2);
		Matcher m=p.matcher(str);
		
		while(m.find())
		{
		int j=0;
		for(int i=0;i<m.start();i++)
		if(str.charAt(i)=='\r')
		j++;
		f.toFront();
		t.replaceText(t4.getText(),m.start()-j,m.end()-j);
		str=t.getText();
		m=p.matcher(str);
		}
		isReplace=false;
		}
		
		break;
case " Find ":
		isFind=false;
		count=0;
		str2=t3.getText();
		savedString2=str2;
		if(str2.length()==0)
		{
		}
		else
		{
		String str;
		str=t.getText();
		
		
		Pattern p=Pattern.compile(str2);
		Matcher m=p.matcher(str);
		if(m.find())
		{
		int j=0;
		for(int i=0;i<m.start();i++)
		if(str.charAt(i)=='\r')
		j++;
		diff=j;
		f.toFront();
		t.select(m.start()-j,m.end()-j);
		}
		count=1;
		if(diff>0)
		isFind=true;
		isReplace=true;
		}
		
		break;
case "Replace":
		if(count==0)
		findNext();
		str2=t3.getText();
		String str=t.getSelectedText();
		//
		if(!str2.equals(savedString2))
		{
		count=0;
		findNext();
		}
		//
		if(isReplace&&str2.equals(str))
		{
		
		if(!isFind)
		t.replaceText(t4.getText(),t.getSelectionStart(),t.getSelectionEnd());
		else
		{
		
		t.replaceText(t4.getText(),t.getSelectionStart()-diff,t.getSelectionEnd()-diff);
		isFind=false;
		diff=0;
		}
		if(count>0)
		count--;
		findNext();
		
		
		}
		
		break;
case "Find Next":
		findNext();
		break;

case "Cancel.": cancel(fadd);
		break;

case "Don't Save":dontSave();
		break;
		
case "OK": cancel(fadd2);
			open();
			break;
case "OK.": cancel(fadd3);
			break;
}
}

void findNext()
{

		str2=t3.getText();
		if(str2.length()==0)
		{
		}
		else
		{
		if(start==true)
		count+=1;
		if(!(str2.equals(savedString2)))
		{
		count=0;
		}
		String str;
		str=t.getText();
		Pattern p=Pattern.compile(str2);
		Matcher m=p.matcher(str);
		int i=0;
		while(i<count)
		{
		m.find();
		i++;
		}
		if(m.find())
		{
		int j=0;
		for(i=0;i<m.start();i++)
		if(str.charAt(i)=='\r')
		j++;

		diff=j;
		
		f.toFront();
		t.select(m.start()-j,m.end()-j);
		savedString2=str2;
		count+=1;
		}
		start=false;
		isFind=true;
		isReplace=true;
		}
	}

public static void main(String args[])
{
	NotePad note=new NotePad();

}

void cancel(Frame f0)
{	
	toOpen=false;
	close=false;
	toExit=false;
	f0.setVisible(false);
	f0.dispose();
	
}

void Exit1(Frame f0)
{
	if(!(saved||dont))
	{
		close=true;
		dont=false;
		dialog();
	}
	else
	{
		f0.setVisible(false);
		f0.dispose();
		System.exit(1);
	}
}

void changeName()
{
f.setTitle("NotePad  "+fileName);
}

public void windowClosing(WindowEvent e)
{
	checkSaved();
	Window w=e.getWindow();
	toExit=false;
	if((Frame)w==f)
		{		
			Exit1(f);
			
		}
	else
	{
		
		w.setVisible(false);
		w.dispose();
	}
}

void find()
{
	Button b,b2,b3;
	f2=new Frame("Find ");
	f2.setLocation(100,100);
	f2.setSize(400,300);
	f2.setLayout(new GridBagLayout());
	GridBagConstraints gbc=new GridBagConstraints();
	gbc.fill=GridBagConstraints.HORIZONTAL;
	Insets i=new Insets(5,5,5,5);
	gbc.insets=i;
	gbc.ipady=10;
	gbc.anchor=GridBagConstraints.SOUTH;
	gbc.weightx=gbc.weighty=1.0;
	l=new Label("Enter Word:");
	f2.add(l,gbc);
	gbc.gridx=1;
	t2=new TextField(12);
	f2.add(t2,gbc);
	//gbc.gridx=0;
	//gbc.gridy=1;
	//gbc.gridwidth=2;
	//b=new Button("Find");
	//b.addActionListener(this);

	//f2.add(b,gbc);
	gbc.gridy=2;
	gbc.gridwidth=1;
	gbc.gridx=0;
	gbc.anchor=GridBagConstraints.NORTH;

	b2=new Button("Find next");
	b2.addActionListener(this);
	f2.add(b2,gbc);
	gbc.gridx=1;
	b3=new Button("Find previous");
	b3.addActionListener(this);
	f2 .addWindowListener(this);
	f2.add(b3,gbc);
	f2.setVisible(true);
}

void replace()
{

	f3=new Frame("Find & Replace ");

	f3.setSize(400,300);
	f3.setLocation(100,100);
	f3.setLayout(new GridBagLayout());
	GridBagConstraints gbc=new GridBagConstraints();
	gbc.fill=GridBagConstraints.HORIZONTAL;
	Insets i=new Insets(5,5,5,5);
	gbc.insets=i;
	gbc.ipady=10;
	gbc.anchor=GridBagConstraints.SOUTH;
	gbc.weightx=gbc.weighty=1.0;
	l2=new Label("Find What:");
	f3.add(l2,gbc);
	t3=new TextField(12);
	gbc.gridx=1;
	f3.add(t3,gbc);
	l3=new Label("Replace with:");
	gbc.gridy=1;
	gbc.gridx=0;
	f3.add(l3,gbc);
	t4=new TextField(12);
	gbc.gridx=1;
	f3.add(t4,gbc);
	
	
	gbc.gridx=0;
	gbc.gridy=2;
	b4=new Button(" Find ");
	b4.addActionListener(this);
	
	f3.add(b4,gbc);
	gbc.gridy=2;
	gbc.gridx=1;
	
	
	
	b5=new Button("Find Next");
	b5.addActionListener(this);
	f3.add(b5,gbc);
	gbc.gridx=0;
	gbc.gridy=3;
	b6=new Button("Replace");
	b6.addActionListener(this);
	f3.add(b6,gbc);
	gbc.gridx=1;

	b7=new Button("Replace All");
	b7.addActionListener(this);
	f3.add(b7,gbc);
	
	f3.addWindowListener(this);
	f3.setVisible(true);
}

void New()
{

	
		if(saved||dont)
		{
			t.setText("");
			saved=false;
			savedString="";
			fileName="NewDocument.txt";
			changeName();
			dont=false;
			ifold=false;
		}

		else
		{
			dialog();
			toExit=true;
			ifold=false;
		}
		
	
	
}

void dialog()
{
	Frame f5=new Frame("Message");
	f5.setSize(400,200);
	f5.setLocation(100,100);
	f5.setLayout(new GridBagLayout());
	f5.addWindowListener(this);
	GridBagConstraints gbc=new GridBagConstraints();
	gbc.gridwidth=2;
	Label l=new Label("Do you want to save file before closing:");
	Insets i=new Insets(4,4,4,4);
	gbc.insets=i;
	f5.add(l,gbc);
	gbc.gridwidth=1;
	gbc.gridy=2;
	gbc.gridx=0;
	gbc.ipadx=gbc.ipady=3;
	Button b=new Button("Save");
	b.addActionListener(this);
	f5.add(b,gbc);
	gbc.gridx=1;
	Button b2=new Button("Don't Save");
	b2.addActionListener(this);
	f5.add(b2,gbc);
	Button b3=new Button("Cancel.");
	b3.addActionListener(this);
	gbc.gridx=2;
	f5.add(b3,gbc);
	fadd=f5;
	f5.setVisible(true);
	dig=true;
	dont=false;
}

void save()
{
	if(!(saved||dont))
	{	
		dont=false;
		if(ifold)
		{	
			try
			{
				FileOutputStream fout=new FileOutputStream(savedDirec+savedFile);
				String str=t.getText();
				for(int i=0;i<str.length();i++)
				{
					fout.write(str.charAt(i));

				}
				fout.close();
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			savedString=t.getText();
			saved=true;
			changeName();
			if(toExit)
			{
				cancel(fadd);
				toExit=false;
				New();
			}
			else if(toOpen)
			{
				open();
				toOpen=false;
				
			}
			else if(close)
			{
				close=false;
				Exit1(f);
			}
			
		}
		
		else
		{
		FileDialog fd=new FileDialog(f,"Save",FileDialog.SAVE);
		fd.setVisible(true);
		String path=fd.getDirectory()+fd.getFile();
		savedDirec=fd.getDirectory();
		savedFile=fd.getFile();
		fileName=fd.getFile();
		
		if(!path.equals("nullnull"))
		{
			try
			{
				FileOutputStream fout=new FileOutputStream(path);
				String str=t.getText();
				for(int i=0;i<str.length();i++)
				{
					fout.write(str.charAt(i));

				}
				fout.close();
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			savedString=t.getText();
			saved=true;
			ifold=true;
			changeName();
			if(toExit)
			{
				cancel(fadd);
				toExit=false;
				New();
			}
			else if(toOpen)
			{
				open();
				toOpen=false;
				
			}
			else if(close)
			{
				close=false;
				Exit1(f);
			}
		
		}
	}
	dont=false;
}
	
}

void saveAs()
{
	FileDialog fd=new FileDialog(f,"Save As",FileDialog.SAVE);
	fd.setVisible(true);
	String path=fd.getDirectory()+fd.getFile();
	if(!path.equals("nullnull"))
	{
		try
		{
			FileOutputStream fout=new FileOutputStream(path);
			String str=t.getText();
			for(int i=0;i<str.length();i++)
			{
				fout.write(str.charAt(i));
			}
			fout.close();
		}
		catch(Exception e)
		{
		}
	}
}

void open()
{	
	toOpen=false;
	if(dig==true)
	{
		cancel(fadd);
		dig=false;
	}
	if(!(saved||dont))
	{
		
		toOpen=true;
		dont=false;
		dialog();
	
	}
	
	else
	{	
		dont=false;
		FileDialog fd=new FileDialog(f,"Open",FileDialog.LOAD);
		fd.setVisible(true);
		String path=fd.getDirectory()+fd.getFile();
		savedDirec=fd.getDirectory();
		savedFile=fd.getFile();
		fileName=fd.getFile();
		ifold=true;
		if(!path.equals("nullnull"))
		{
				
			try
			{
				String st="";
				fileName=fd.getFile();
				FileInputStream fin=new FileInputStream(path);
				int ch;
			
				while((ch=fin.read())!=-1)
				{
					st=st+(char)ch;
				}
				t.setText(st);
				savedString=st;
				changeName();
				fin.close();
			}
			catch(Exception e)
			{	
				fileName="NewDocument.txt";
				changeName();
				dialog2();
				System.out.println(e.getMessage());
			}
			
		}	
		
		
	}

}
	
void dialog2()
{
	Frame ff=new Frame("Not found");
	ff.setSize(400,200);
	ff.setLocation(100,100);
	ff.setLayout(new GridBagLayout());
	ff.addWindowListener(this);
	GridBagConstraints gbc=new GridBagConstraints();
	gbc.gridwidth=2;
	Label ll=new Label("File Not Found");
	ff.add(ll,gbc);
	gbc.gridy=2;
	gbc.gridx=1;
	gbc.ipadx=gbc.ipady=5;
	gbc.gridwidth=1;
	Button bb=new Button("OK");
	ff.add(bb,gbc);
	bb.addActionListener(this);
	ff.setVisible(true);
	fadd2=ff;
}

void checkSaved()
{
	if(savedString.equals(t.getText())||t.getText().length()==0)
	{
		saved=true;
	}
	else
	{
		saved=false;
	}

}

void dontSave()
{	
	dont=true;
	
		if(toExit)
			{
				cancel(fadd);
				toExit=false;
				New();
			}
		if(toOpen)
			{
				open();
				toOpen=false;
			}
		if(close)
			{
				close=false;
				Exit1(f);
			}
			
}

void dialog3()
{
	Frame ff=new Frame("Not found");
	ff.setSize(400,200);
	ff.setLocation(100,100);
	ff.setLayout(new GridBagLayout());
	ff.addWindowListener(this);
	GridBagConstraints gbc=new GridBagConstraints();
	gbc.gridwidth=2;
	Label ll=new Label("Text Not Found");
	ff.add(ll,gbc);
	gbc.gridy=2;
	gbc.gridx=1;
	gbc.ipadx=gbc.ipady=5;
	gbc.gridwidth=1;
	Button bb=new Button("OK.");
	ff.add(bb,gbc);
	bb.addActionListener(this);
	ff.setVisible(true);
	fadd3=ff;
}
}