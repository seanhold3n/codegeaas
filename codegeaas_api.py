from flask import Flask
from flask import Response
from flask import request, redirect, url_for
import numpy as np
import StringIO

app = Flask(__name__)


@app.route('/')
def index():
    return 'Hello, world!\n'


@app.route('/api', methods=['GET', 'POST'])
def api():
    if request.method == 'GET':
        return redirect(url_for('api_about'))
    else:
        # Get input array
        #reqArray = np.array([[1,-1,3,-3],[-1,0,-2,1],[2,2,4,0]])
        #reqArray = np.fromstring(request.data)
        regArray = np.genfromtxt(StringIO(request.data), delimiter=',')
        print(np.array_str(regArray))

        # Do Gaussian things
        # TODO

        # Return
        responseArray = np.array([1,1,-1])
        responseStr = np.array_str(responseArray)
        print(responseStr)

        sioOut = StringIO()
        np.savetxt(sioOut, responseArray, delimiter=',')
        responseStr = sioOut.getvalue()
        print(responseStr)

        return Response(responseStr, status=200, mimetype="text/csv")
        #return Response("-1")


@app.route('/api/about.html')
def api_about():
    return 'This is our API!'

if __name__ == '__main__':
    app.run()
