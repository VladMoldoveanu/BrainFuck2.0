Some useful functions:

Function 0: Add numbers @1 and @2 in @1
Cursor end point: @1
~
    >>[-<+>]<
~

Function 1: Multiply numbers @1 and @2 in @3
Cursor end point: @3
~
    [-]>->[->+>+<<<<+>>]< Move @2 to @0 @3 and @4
    [
        ->>>[-<+>]< Add @4 to @3
        <<<[->>>>+>+<<<<<]>>>>>[-<<<<<+>>>>>]<<<< Copy @0 to @4
    ]
    >> Move cursor to result
~

Function 2: Rise @1 to @2 in @2
Cursor end point: @2
~
    [-]>>[-<<+>>] Move @2 in @0
    +< Set @2 to 1
    [->>+<<]< Move @1 to @3
    [
        ->!!- Set multiplication in @1
        /\\ Multiply @2 and @3
        >[-]<[->+<]< Move @1 in @2
    ]
    >>
~