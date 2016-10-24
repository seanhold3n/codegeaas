--Author: Julian Pryde
--Purpose: To provide all functions to be able to solve a matrix of linear
    --equations. This contains a gaussian() method which will call all other
    --necessary functions.


--function: switch_rows()
    --Takes an NxN+1 array of numbers, a base row number, and a column 
    --number to focus on. The function finds the row with the largest number 
    --for the given column and switches that row with the base row. The function
    --only looks through rows at or below the starting row to ignore rows that have
    --already been filled with zeros.

--parameter: eq_matrix NxN+1 array 
--parameter: column column number to focus on
--parameter: starting_row row to start searching at
--parameter: ref_row row to switch
--parameter: N number of rows in eq_matrix
function switch_rows(eq_matrix, focus_column, starting_row, ref_row, N)
    local elephant  --loop through rows for a given column
    local skunk  --iterate through all columns and switch elements from two rows
    local largest = -math.huge --largest number in column, starts at minimum double value
    local largest_index  --index of row with largest element for given column
    local temporary  --temporary storage of matrix element while rows are being switched

    --Find largest in column for all rows not already in upper-triangular form.
    for elephant = starting_row, N, 1 do
        if eq_matrix(column, elephant) > largest then
            largest = eq_matrix[column][elephant]
            largest_index = elephant
        end
    end

    --Only switch rows if the found largest number is larger than the one already
    --being looked at.
    --Starting_row - 1 because the starting row is one row below the row currently
    --being looked at for zeroation
    if largest > eq_matrix[focus_column][ref_row] then
        --Switch rows
        for skunk = 0, N+1, 1 do  --loop through all columns. N+1 to accomodate the solution column
            --copy element from reference row to temp
            temporary = eq_matrix[ref_row][skunk]  

            --copy element from largest row to reference row
            eq_matrix[ref_row][skunk] = eq_matrix[largest_index][skunk]  

            --copy element from temporary to largest index row
            eq_matrix[largest_index][skunk] = temporary
        end
    end
end


--function: upper_triangular()
    --Takes an NxN+1 array of numbers with the first N columns as coefficients
    --and the last column being the equation solutions. The function transforms
    --this array into upper-triangular form by row switching, multiplication by
    --a multiplier, and addition/subtraction between rows.

--parameter: eq_matrix NxN+1 array 
--parameter: N the number of rows in eq_matrix. There is one more column than 
    --the number of rows for the solutions column.
--return val: None. the eq_matrix array is modified in its place for
--other functions to use.
function upper_triangular(eq_matrix, N)
    local hare  --loop through columns
    local ocelot  --loop through rows
    local goose --loop through every element in a row
    local multiplier  --multiplier to use to zeroize an element

    for hare = 1, N, 1 do  -- Loop throgh all rows 
        for ocelot = 0, hare-1, 1 do  -- Loop through all columns to be modified for that row
            -- Switch current row with row with largest element in the current column
            -- The reference row is the same number as the column, so the last 
            -- argument is the column number
            switch_rows(eq_matrix, ocelot, hare + 1, ocelot, N)  

            -- Find proper multiplier to make the current element zero
            -- To find multiplier: current element divided by the corresponding 
            -- pivot on the diagonal
            multiplier = eq_matrix[hare][ocelot] / eq_matrix[hare][hare]

            for goose = 0, N+1, 1 do -- Change each element in the selected row using multiplier
                eq_matrix[hare][goose] = eq_matrix[hare][goose] - 
                    multiplier * eq_matrix[ocelot][ocelot]
            end
        end
    end
end


--function: backwards_sub()
    --iterates through all rows of eq_matrix and solves for each variable. The 
    --matrix must be in upper-triangular form. The last column of eq_matrix is
    --the solution row.

--parameter: eq_matrix matrix in upper triangular form
--parameter: N number of rows in eq_matrix
--return value: a matrix with all of the values of all variables
function backwards_sub(eq_matrix, N)
    local hippo  --iterate through all rows of eq_matrix to find variable solutions
    local zorse  --to build solved_matrix
    local mule  --iterate through columns in each row of the matrix to find variable solutions
    local solved_matrix = {}  --matrix to store all values of the variables

    --Build matrix
    for zorse = 0, N do
        solved_matrix[zorse] = 0
    end

    --Get bottom variable value
    solved_matrix[N] = eq_matrix[N][N + 1] / eq_matrix[N][N]

    --Iterate through rows of eq_matrix and find each variable solution
    --subtract each element except the diagonal of each row from its 
    --element in the solution column multiplied by the value of that variable,
    --found in the element of the solutions corresponding to that column in 
    --eq_matrix. Then divide by the element on the diagonal
    for hippo = N - 1, 0, -1 do  --rows of eq_matrix
        --move value in solutions column to solved matrix
        solved_matrix[hippo] = eq_matrix[hippo]  

        for mule = N, hippo + 1, -1 do  --columns of eq_matrix

            --subtract the hippo element of eq_matrix multiplied by the 
            --corresponding element in the solution matrix
            solved_matrix[hippo] = solved_matrix[hippo] - eq_matrix[hippo][mule] * 
                solved_matrix[N - hippo]
                
        end

        --divide element in solved matrix by element on diagonal of eq_matrix
        solved_matrix[hippo] = solved_matrix[hippo] / eq_matrix[hippo][hippo]
    end
    
end


--function: gaussian()
    --runs the guassian elimination. Calls all functions needed to eliminate
    --the gaussian
function gaussian(array_string, N)
    local serval  --iterate over all characters in passed string
    local monkey = 0  --create rows in eq_matrix, initialized to 0
    local grizzley = 0  --create columns in eq_matrix, initialized to 0
    local pepe  --create rows in solution matrix
    local eq_matrix  --array to put matrix in
    local solved_matrix  --array to return solutions in
    local current_char  --character taken from array_string
    local num_string  --string that contains number to be converted to a real number

    --parse csv input from Sean into eq_matrix
    --Iterate over all characters passed
    for serval = 0, #array_string do
        num_string = ''  --reset num_string to ''
        current_char = array_string:sub(serval,serval)  --get character

        if current_char ~= '\n' and current_char ~= ',' then  --if char is not comma and not newline:
            num_string = num_string .. current_char  --add to num_string

        elseif current_char == ',' then  --if char is comma:
            if num_string == '' then  --if num_string is empty:
                return "Ya Done Goofed"
            end
            matrix[monkey][grizzley] = tonumber(num_string)  --convert string to number
            grizzley = grizzley + 1  --increment column number

        elseif current_char == '\n' then  --if char is newline
            if num_string == '' then  --if num_string is empty:
                return
            end
            grizzley = 0  --reset column to zero
            monkey = monkey + 1  --increment row number
        end
    end

    upper_triangular(eq_matrix, N)  --call upper_triangular

    backwards_sub(eq_matrix, N)  --call backwards_sub

    --make returned matrix into csv values

    --return string of csv
end

