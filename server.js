const http = require("http");
const fs = require("fs");
const path = require("path");
const { spawn } = require("child_process");


const server = http.createServer((req, res) => {


    // Open the main HTML page
    if (req.url === "/" && req.method === "GET") {

        const filePath = path.join(
            __dirname,
            "index.html"
        );


        fs.readFile(
            filePath,
            (error, data) => {

                if (error) {

                    res.writeHead(500);

                    res.end(
                        "Error loading page."
                    );

                    return;
                }


                res.writeHead(200, {

                    "Content-Type":
                        "text/html"

                });


                res.end(data);

            }
        );


        return;
    }


    // Calculate tax
    if (
        req.url === "/calculate" &&
        req.method === "POST"
    ) {

        let body = "";


        // Receive data from the browser
        req.on(
            "data",
            chunk => {

                body += chunk;

            }
        );


        req.on(
            "end",
            () => {

                const data =
                    JSON.parse(body);


                // Start the Python program
                const python = spawn(
                    "python",
                    ["tax_calculator.py"]
                );


                // Send information to Python
                const input =
                    data.name + "\n" +
                    data.salary + "\n" +
                    data.otherIncome + "\n" +
                    data.months + "\n" +
                    data.taxWithheld + "\n";


                python.stdin.write(input);

                python.stdin.end();


                // Store Python result
                let output = "";


                python.stdout.on(
                    "data",
                    data => {

                        output +=
                            data.toString();

                    }
                );


                // Send Python result back to browser
                python.on(
                    "close",
                    code => {

                        res.writeHead(
                            200,
                            {
                                "Content-Type":
                                    "application/json"
                            }
                        );


                        res.end(
                            JSON.stringify({

                                result: output

                            })
                        );

                    }
                );

            }
        );


        return;
    }


    // If page does not exist
    res.writeHead(404);

    res.end(
        "Page not found."
    );

});


// Start server
server.listen(
    3000,
    () => {

        console.log(
            "BIR Tax System is running!"
        );

        console.log(
            "Open http://localhost:3000"
        );

    }
);