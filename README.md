# Code GEaaS: Gaussian Elimination as a Service
A web service that solves systems of linear equations using Gaussian elimination.

# Requirements
Code GEaaS uses the following:
 * [Python 2.7](https://www.python.org/download/releases/2.7/)
     * Flask (for handling web requests)
     * Numpy (for matrix operations)
     * [Lunatic Python](https://github.com/bastibe/lunatic-python) for Lua interop
 * [Lua](https://www.lua.org/download.html) (for computation)

# Setup
Run `pip install -r requirements.txt` to install Python dependencies, and execute `codegeaas_api.py` to start the web service.

# API Calls

Matricies should be formed as comma-separated values.  For example, the following equations...
 ```
  a -  b + 3c = -3
 -a      - 2c = 1
 2a + 2b + 4c = 0
 ```
 
 ...should be formed as follows:
 ```
  1,-1, 3,-3
 -1, 0,-2, 1
  2, 2, 4, 0
 ```
 ...where numbers within a row are separated by commas, and rows are separated by lines.
 
 This string shall be uploaded to the API endpoint as data in a POST request.  The server will respond with a comma-separated list of the solutions.
 
 Sample request:
 ```bash
 curl -H "Content-type: text/plain" \
-X POST http://localhost:5000/messages -d '1,-1,3,-3\n-1,0,-2,1\n2,2,4,0'
 ```

Sample response:
`1,1,-1`
