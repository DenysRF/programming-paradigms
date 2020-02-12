Program gauss;

Var x, sum: Integer;

Begin
    In("x? ", x);
    While x > 0 Do
    Begin
        sum := sum + x;
        x := x - 1
    End;
    Out("Sum: ", sum)
End.