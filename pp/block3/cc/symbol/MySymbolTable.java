package block3.cc.symbol;

import java.util.HashSet;
import java.util.Stack;

public class MySymbolTable implements SymbolTable {

    private Stack<HashSet<String>> scopes;

    public MySymbolTable() {
        this.scopes = new Stack<>();
        // Initialize with outer scope
        this.scopes.push(new HashSet<>());
    }

    /**
     * Adds a next deeper scope level.
     */
    @Override
    public void openScope() {
        scopes.push(new HashSet<>());
    }

    /**
     * Removes the deepest scope level.
     *
     * @throws RuntimeException if the table only contains the outer scope.
     */
    @Override
    public void closeScope() {
        if (scopes.size() == 1) {
            throw new RuntimeException();
        }
        scopes.pop();
    }

    /**
     * Tries to declare a given identifier in the deepest scope level.
     *
     * @param id
     * @return <code>true</code> if the identifier was added,
     * <code>false</code> if it was already declared in this scope.
     */
    @Override
    public boolean add(String id) {
        return scopes.peek().add(id);
    }

    /**
     * Tests if a given identifier is in the scope of any declaration.
     *
     * @param id
     * @return <code>true</code> if there is any enclosing scope in which
     * the identifier is declared; <code>false</code> otherwise.
     */
    @Override
    public boolean contains(String id) {
        for (HashSet<String> s : scopes) {
            if (s.contains(id)) {
                return true;
            }
        }
        return false;
    }
}
