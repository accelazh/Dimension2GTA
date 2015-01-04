package subGame.breakBrick.mapEdition;

import java.io.*;
import javax.swing.filechooser.FileFilter;

/**
 * 
 * ��������ר���ļ�
 */
public class BrickTypesFileFilter extends FileFilter 
{
	//for debug
	private static final boolean debug=false;
	
	//brickTypes�Ĵ洢��ʽ
	public static final String formation="bricktypes";
	
	@Override
	public boolean accept(File f)
	{
		if(debug)
		{
			System.out.println("====brickTypesFileFilter launched====");
		}
		if(null==f)
		{
			return false;
		}
		
		if (f.isDirectory()) 
		{
			return true;
	    }

		String extension = getExtension(f);
		
		if(debug)
		{
			if(extension!=null)
			{
				System.out.println("extension: "+"~"+extension+"~");
				System.out.println("formation: "+"~"+formation+"~");
				System.out.println("extension.equals(formation): "+"~"+extension.equals(formation)+"~");
			}
			else
			{
				System.out.println("extension==null");
			}
		}
		
		if (extension != null) 
		{
			if (extension.equals(formation.toLowerCase()))
			{
				return true;
			} 
		}

		return false;
	}
	
	/**
	 * ���������һ���ļ��ļ������䣬�ı�
	 * ��չ����ʹ���Ϊ������������ϸ�ͨ
	 * �����ļ�
	 * @param f
	 * @return
	 */
	public File adjustToAccuratelyAcceptedFile(File f)
	{
		if(null==f)
		{
			return null;
		}
		
		//��ȡ
		String path=f.getParent();
		StringBuffer nameBuffer=new StringBuffer(f.getName());
		
		//���˷Ƿ��ַ�
		for(int i=0;i<nameBuffer.length();i++)
		{
			if(!isLegalChar(nameBuffer.charAt(i)))
			{
				nameBuffer.deleteCharAt(i);
				i--;
			}
		}
		
		//�޸���չ��
		int pos=nameBuffer.toString().lastIndexOf('.');
		if(pos<0)
		{
			if(0==nameBuffer.length())
			{
				return null;
			}
			nameBuffer.append('.'+formation);
		}
		else
		{
			nameBuffer.delete(pos,nameBuffer.length());
			if(0==nameBuffer.length())
			{
				return null;
			}
			nameBuffer.append('.'+formation);
		}
		
		//����
		return new File(path+'/'+nameBuffer.toString());
	}
	
	//��������ж�һ���ַ��Ƿ��ǺϷ����ļ����ַ�
	public boolean isLegalChar(char c)
	{
		if((c>='a'&&c<='z')
			||(c>='A'&&c<='Z')
			||(c>='0'&&c<='9')
			||(' '==c)
			||('.'==c)
			||('-'==c)
			||('_'==c)
			||('$'==c)
			||('#'==c))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//�����f���ļ��е�ʱ�򷵻�false
	public boolean acceptAccurately(File f)
	{
		if(debug)
		{
			System.out.println("====brickTypesFileFilter launched====");
		}
		if(null==f)
		{
			return false;
		}
		
		if (f.isDirectory()) 
		{
			return false;
	    }

		String extension = getExtension(f);
		
		if(debug)
		{
			if(extension!=null)
			{
				System.out.println("extension: "+"~"+extension+"~");
				System.out.println("formation: "+"~"+formation+"~");
				System.out.println("extension.equals(formation): "+"~"+extension.equals(formation)+"~");
			}
			else
			{
				System.out.println("extension==null");
			}
		}
		
		if (extension != null) 
		{
			if (extension.equals(formation.toLowerCase()))
			{
				return true;
			} 
		}

		return false;
	}
	
	
	public static String getExtension(File f)
	{
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

	@Override
	public String getDescription() 
	{
		return "bricktypes";
	}


}
