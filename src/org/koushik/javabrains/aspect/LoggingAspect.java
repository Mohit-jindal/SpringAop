package org.koushik.javabrains.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.koushik.javabrains.model.Circle;

@Aspect
public class LoggingAspect {

	//@Before("execution(public String getName())")
	//@Before("execution(public * get*())")	
	//@Before("execution(* get*(*))") //for any type of input argument
	//@Before("execution(* get*(..))") //will have argument or not	
	//@Before("execution(* org.koushik.javabrains.*.get*(..))") //for all classes inside this package
	//@Before("execution(* get*())")	//for zero arguments
	//@Before("allGetters()")
	//@After("allGetters()")
	//@Before("allGetters() && allCircleMethods()")
	@Before("allCircleMethods()")
	public void LoggingAdvice(JoinPoint joinPoint)
	{
		//System.out.println("Advice run. Get method called");
		System.out.println(joinPoint.toString());
		Circle circle = (Circle) joinPoint.getTarget();
		circle.setName("Mohit");
		System.out.println(circle.getName());
	}
	
	//@Before("args(String)")
	//@Before("args(name)")
	//@After("args(name)") //advice will run after method whether successfully run or not
	//@AfterReturning("args(name)") //advice will run only after method successfully run
	@AfterReturning(pointcut = "args(name)" , returning = "returnValue") //for method which takes String as an argument and will return String value as output, because we defined type String in method argument
	public void stringArgumentMethods(String name, String returnValue) {
		System.out.println("A method that takes String arguments has been called. The value is: "+ name + ". The return value is: " + returnValue);
	}

	//@AfterThrowing("args(name)") //advice will run when an exception is thrown during method call
	@AfterThrowing(pointcut = "args(name)", throwing = "ex") //advice will run when an exception is thrown during method call
	public void exceptionAdvice(String name, Exception ex) {
		System.out.println("An exception has been thrown, " + ex);
	}

	//@Around("allGetters()")
	@Around("@annotation(org.koushik.javabrains.aspect.Loggable)")
	public Object myAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) //mandatory parameter for Around annotation 
	{
		Object returnValue = null;
		try 
		{
			System.out.println("Before advice");
			returnValue = proceedingJoinPoint.proceed(); //this is the place where actual target method is called, this is optional to call this
			//We can modify this object but we can modify same in afterReturning advice
			System.out.println("After Returning");
		} 
		catch (Throwable e) 
		{
			System.out.println("After Throwing");
		} 
		
		System.out.println("After Finally");
		return returnValue;
	}
	
	//@Before("execution(* get*())")	//for zero arguments
	//@Before("allGetters()")
	public void secondAdvice()
	{
		System.out.println("Second advice executed");
	}
	
	@Pointcut("execution(* get*())")
	public void allGetters() {}

	//@Pointcut("execution(* * org.koushik.javabrains.model.Circle.*(..))")
	@Pointcut("within(org.koushik.javabrains.model.Circle)")
	//@Pointcut("within(org.koushik.javabrains.model.*)")	//for all the classes inside model package but not for sub packages
	//@Pointcut("within(org.koushik.javabrains.model..*)")	//for all the classes inside model package also for sub packages
	public void allCircleMethods() {}
	
	public void loggingAdvice()
	{
		System.out.println("Logging advice aspect");
	}
}
