package cn.com.bocosoft.tag.helper;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class HelperTag extends SimpleTagSupport {
    private Object params;
    private static String className;
    private String methodName;
    private static String writer;
    private static Object backValue;
    
    public void doTag() throws JspException, IOException{
         try {
             getMethodObejct(methodName,className,params);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        getJspContext().setAttribute(getWriter(), backValue);
        getJspBody().invoke(null);
        super.doTag();
    }
    
    
    /**
    *
    * @param MethodName
    * @param o           调用此方法的对象
    * @param paras       调用的这个方法的参数参数列表
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
    */
    public static void  getMethod(String MethodName,Object o,Object []paras) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException{
        Class c[]=null;
        if(paras!=null){//存在
            int len=paras.length;
            c=new Class[len];
            for(int i=0;i<len;++i){
                c[i]=paras[i].getClass();
            }
        }
        try {
            //利用反射生成对象
            Class targetClass = Class.forName((String) o);
            Object targetObj = targetClass.newInstance();
            
            Method method = targetObj.getClass().getDeclaredMethod(MethodName,c);
            //Method method = targetClass.getDeclaredMethod(MethodName,c);
            try {
                backValue = method.invoke(targetObj,paras);//调用o对象的方法
            } catch (IllegalAccessException ex) {
                //Logger.getLogger(CommonHelper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                //Logger.getLogger(CommonHelper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                //Logger.getLogger(CommonHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NoSuchMethodException ex) {
            //Logger.getLogger(CommonHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            //Logger.getLogger(CommonHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
    
    public static void getMethodObejct(String MethodName,Object className,Object params) throws IllegalArgumentException, InvocationTargetException{
        try {
            getMethod(MethodName,className,new Object[]{params});
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
           // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void setParams(Object params) {
        this.params = params;
    }

    public Object getParams() {
        return params;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public final void setWriter(String writer) {
        this.writer = writer;
    }

    public final  String getWriter() {
        return writer;
    }
}