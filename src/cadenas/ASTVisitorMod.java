package cadenas;

import java.util.ArrayList;
import java.util.HashMap;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.WhileStatement;

public class ASTVisitorMod extends ASTVisitor{
	public ArrayList<String> variables = new ArrayList<String>();
	public HashMap<String, Integer> names = new HashMap<String, Integer>();
	public HashMap<String, Integer> oprt = new HashMap<String, Integer>();
	public HashMap<String, Integer> declaration = new HashMap<String, Integer>();
	CompilationUnit compilation=null;
	
	public boolean visit(IfStatement node)
	{	
		if (!this.oprt.containsKey("if"))
		{
			this.oprt.put("if", 1);
		}
		else
		{
			this.oprt.put("if", this.oprt.get("if")+1);
		}
		
		return true;
	}
	
	public boolean visit(SwitchStatement node)
	{	
		if (!this.oprt.containsKey("switch"))
		{
			this.oprt.put("switch", 1);
		}
		else
		{
			this.oprt.put("switch", this.oprt.get("switch")+1);
		}
		
		return true;
	}
	
	public boolean visit(ThrowStatement node)
	{	
		if (!this.oprt.containsKey("throw"))
		{
			this.oprt.put("throw", 1);
		}
		else
		{
			this.oprt.put("throw", this.oprt.get("throw")+1);
		}
		
		return true;
	}
	
	public boolean visit(ForStatement node)
	{
		if (!this.oprt.containsKey("for"))
		{
			this.oprt.put("for", 1);
		}
		else
		{
			this.oprt.put("for", this.oprt.get("for")+1);
		}
		
		return true;
	}
	
	public boolean visit(WhileStatement node)
	{
		if (!this.oprt.containsKey("while"))
		{
			this.oprt.put("while", 1);
		}
		else
		{
			this.oprt.put("while", this.oprt.get("while")+1);
		}
		
		return true;
	}
	
	public boolean visit(DoStatement node)
	{
		if (!this.oprt.containsKey("do"))
		{
			this.oprt.put("do", 1);
		}
		else
		{
			this.oprt.put("do", this.oprt.get("do")+1);
		}
		
		return true;
	}
	
	public boolean visit(InfixExpression node)
	{			
		if (!this.oprt.containsKey(node.getOperator().toString()))
		{
			this.oprt.put(node.getOperator().toString(), 1);
		}
		else
		{
			this.oprt.put(node.getOperator().toString(), this.oprt.get(node.getOperator().toString())+1);		
		}				
		return true;
	}
	
	public boolean visit(PostfixExpression node)
	{		
		if (!this.oprt.containsKey(node.getOperator().toString()))
		{
			this.oprt.put(node.getOperator().toString(), 1);
		}
		else
		{
			this.oprt.put(node.getOperator().toString(), this.oprt.get(node.getOperator().toString())+1);
		}	
		return true;
	}
	
	public boolean visit(Assignment node)
	{			
		if (!this.oprt.containsKey(node.getOperator().toString()))
		{
			this.oprt.put(node.getOperator().toString(), 1);
		}
		else
		{
			this.oprt.put(node.getOperator().toString(), this.oprt.get(node.getOperator().toString())+1);
		}	
		
		return true;
	}
	
	
	public boolean visit(SingleVariableDeclaration node) {
		if(node.getInitializer()!=null)
		{
			if (!this.oprt.containsKey("="))
			{
				this.oprt.put("=", 1);
			}
			else
			{
				this.oprt.put("=", this.oprt.get("=")+1);
			}
		}
		
		return true;
	}
	
	public boolean visit(VariableDeclarationFragment node) {		
		
		if(node.getInitializer()!=null)
		{	
			if (!this.oprt.containsKey("="))
			{
				this.oprt.put("=", 1);
			}
			else
			{
				this.oprt.put("=", this.oprt.get("=")+1);
			}
		}
		
		return true;
	}
	
	public boolean visit(PrefixExpression node)
	{		
		if (!this.oprt.containsKey(node.getOperator().toString()))
		{
			this.oprt.put(node.getOperator().toString(), 1);
		}
		else
		{
			this.oprt.put(node.getOperator().toString(), this.oprt.get(node.getOperator().toString())+1);
		}
		
		return true;
	}
	
	// Override visit the SimpleNames nodes. 
	// if the SimpleName doesn't exist in the names hashmap, insert it, otherwise, increment the count field.
	public boolean visit(SimpleName node) {
		//System.out.println("identifier : " + node.getIdentifier());
		//System.out.println(this.variables);
		if(this.variables.contains(node.getIdentifier())) {
			if (!this.names.containsKey(node.getIdentifier()))
			{
				this.names.put(node.getIdentifier(),1);
			}
			else
			{
				this.names.put(node.getIdentifier(), this.names.get(node.getIdentifier())+1);
			}
		}	
		return true;
	}
	
	// Override visit the string literal nodes. 
		// if the string literal doesn't exist in the names hashmap, insert it, otherwise, increment the count field.
		public boolean visit(StringLiteral node) {	
			
			if (!this.names.containsKey(node.getLiteralValue()))
			{
				this.names.put(node.getLiteralValue(),1);
			}
			else
			{
				this.names.put(node.getLiteralValue(), this.names.get(node.getLiteralValue())+1);
			}
			return true;
		}
		
		// Override visit the character literal nodes. 
		// if the character literal doesn't exist in the names hashmap, insert it, otherwise, increment the count field.
		public boolean visit(CharacterLiteral node) {			
			
			if (!this.names.containsKey(Character.toString(node.charValue())))
			{
				this.names.put(Character.toString(node.charValue()),1);
			}
			else
			{
				this.names.put(Character.toString(node.charValue()), this.names.get(Character.toString(node.charValue()))+1);
			}
			
			return true;
		}

		
		
		// Override visit the boolean literal nodes. 
		// if the boolean literal doesn't exist in the names hashmap, insert it, otherwise, increment the count field.
		public boolean visit(BooleanLiteral node) {	
			
			if (!this.names.containsKey(Boolean.toString(node.booleanValue())))
			{
				this.names.put(Boolean.toString(node.booleanValue()),1);
			}
			else
			{
				this.names.put(Boolean.toString(node.booleanValue()), this.names.get(Boolean.toString(node.booleanValue()))+1);
			}
			
			
			return true;
		}
		
		
		
		// Override visit the Number literal nodes. 
		// if the Number literal doesn't exist in the names hashmap, insert it, otherwise, increment the count field.
		public boolean visit(NumberLiteral node) {	
			if (!this.names.containsKey(node.getToken()))
			{
				this.names.put(node.getToken(),1);
			}
			else
			{
				this.names.put(node.getToken(), this.names.get(node.getToken())+1);
			}
			
			return true;
		}
}