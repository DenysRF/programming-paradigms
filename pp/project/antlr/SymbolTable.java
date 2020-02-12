package project.antlr;

import java.util.HashMap;
import java.util.Stack;

/**
 * The SymbolTable keeps a Stack of HashMaps that contain variable names with their unique address
 * This address is used for memory allocation in sprockell
 * The SymbolTable is used to check whether variable identifiers are in scope.
 * @author denys, yernar
 */
public class SymbolTable {
    // K = variable name, V = unique memory address
    private Stack<HashMap<String, Integer>> scopes;

    public SymbolTable() {
        this.scopes = new Stack<>();
        this.scopes.push(new HashMap<>());
    }

    /**
     * Pushes an empty scope (HashMap with (String, Integer) as key, value) to the scopes Stack
     */
    public void openScope() {
        scopes.push(new HashMap<>());
    }

    /**
     * Prints the content of the scopes stack to System.out for testing purposes
     */
    public void print() {
        for (int i = scopes.size()-1; i >= 0; i--) {
            System.out.println(scopes.get(i).toString());
        }
    }

    /**
     * Permanently closes the current scope
     * @throws RuntimeException when we close the last scope, meaning that something went very wrong.
     */
    public void closeScope() {
        if (scopes.size() == 1) {
            throw new RuntimeException();
        }
        scopes.pop();
    }

    /**
     * @param id the name of the identifier
     * @param i unique memory address
     * @return false if id already present in the map
     * otherwise put the id number pair into the map
     */
    public boolean add(String id, int i) {
        HashMap<String, Integer> map = scopes.peek();
        if (map.containsKey(id)) {
            return false;
        } else {
            map.put(id, i);
            return true;
        }
    }

    /**
     * Gets the unique memory address of the given identifier for the closest corresponding identifier in the
     * scopes stack.
     * @param id the name of the identifier
     * @return the unique memory address for the given identifier
     */
    public int get(String id){
        for (int i = scopes.size()-1; i >= 0; i--) {
            if (scopes.get(i).containsKey(id)) {
                return scopes.get(i).get(id);
            }
        }
        return -1;
    }

    // Iterate stack backwards to try to find the most recent declaration of id
    /**
     * @param id the identifier you want to know is in the the stack
     * @return <true> if a hashmap in the scopes stack contains the given id, otherwise <false>
     */
    public boolean contains(String id) {
        for (int i = scopes.size()-1; i >= 0; i--) {
            if (scopes.get(i).containsKey(id)) {
                return true;
            }
        }
        return false;
    }
}