[ When a '[' is the first character in a file, the whole
thing between the square brackets will be ignored, even <>-+,.[~|
but not ]

Any characters except the special ones will be ignored

Functions can be implemented between those signs
~
    >
    +++++ +++++
    [
        >+
        >+++
        >+++++++
        >++++++++++
        <<<<-
    ]
    >>>++.
    >+.
    +++++++.
    .
    +++.
    <<++.
    >+++++++++++++++.
    >.
    +++.
    ------.
    --------.
    <<+.
    <.
    <<
~

Functions can be called by this sign
|

Functions are labeled with numbers starting at 0
The function called is the one with the assigned number
equal to the one at the current pointer

When entering functions the pointer remains at the same place
and no data is modified
Functions are basically sequences of operations
Calling the same function even from the same point can generate
different results
|

Calling a function with a backslash will result in creating a new array
copying the number at the pointer in it and making the function execute there
After it finishes it returns to the initial array replacing the call
point with the value at the last point
/

Calling a function with a backslash followed by a number of forward slashes
will result in a number of elements to the right of the point equal to the number of
forward slashes copied to the new array
Examples in funtest

Recursive functions are allowed
Also
they can call functions that don't exist yet
~
    >/
~

Exclamation mark sets at the current point the total number of functions
for easily calling the last ones
This will call the last function
which will call Hello world
!->[-]<|

Multiple exclamation mark saves the number of functions during compile
so putting double exclamation marks in a function gets its number
Useful for larger files with more functions

Clear the scene for easy use afterwards
>[-]>[[-]>]<<<<<<-