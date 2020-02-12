package project.antlr;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Syntax Tree
 * @author denys, yernar
 */
public class AST {
    private String name;
    private List<AST> children;

    public AST(String name) {
        children = new ArrayList<>();
        this.name = name;
    }

    /**
     * The method adds a given AST to the current AST
     * @param child
     */
    public void addChild(AST child) {
        children.add(child);
    }

    /**
     * The method returns all the child ASTs of current AST
     * @return list of ASTs of current AST
     */
    public List<AST> getChildren (){
        return children;
    }

    /**
     * The method returns the name of this AST
     * @return name of this AST
     */
    public String getName() {
        return name;
    }

    /**
     * Converts this AST with all its child ASTs into the string
     * @return string representation of this AST
     */
    public String toString() {
        String result = "";
        for (AST a : children){
            result = result + " " + a.getName() + a.children.toString();
        }
        return result;
    }
}
