$(document).ready(function () {
    var title = $(".title");
    var terminal = $(".terminal");
    var prompt = "➜";
    var path = "~";
    var commandHistory = [];
    var historyIndex = 0;
    var command = "";

    title.text('tycoding' + "@" + osName + ": ~ (redis-cli)");
    var date = new Date().toString();
    date = date.substr(0, date.indexOf("GMT") - 1);
    printf("Last login: " + date + " on febs, type `help` for help\n");
    displayPrompt();

    var commands = [{
        name: "clear",
        function: clear
    }, {
        name: "help",
        function: help
    }, {
        name: "echo",
        function: echo
    }, {
        name: "keys",
        function: keys
    }, {
        name: "get",
        function: get
    }, {
        name: "exists",
        function: exists
    }, {
        name: "pttl",
        function: pttl
    }, {
        name: "pexpire",
        function: pexpire
    }, {
        name: "set",
        function: set
    }, {
        name: "del",
        function: del
    }];

    // system command

    function clear() {
        terminal.text("");
        printc();
    }

    function echo(args) {
        var str = args.join("");
        printf(str + "\n");
        printc();
    }

    function erase(n) {
        command = command.slice(0, -n);
        terminal.html(terminal.html().slice(0, -n));
    }

    function clearCommand() {
        if (command.length > 0) {
            erase(command.length);
        }
    }

    // redis command
    function keys(args) {
        args = args.join("");
        if (!hasArgs(args)) {
            errorArgs('keys');
        } else $.get("/redis/keys", {
            'arg': args
        }, function (r) {
            console.log(r);
            var data = r;
            if (data.length) {
                for (var i = 0; i < data.length; i++) {
                    printf('"' + data[i] + '"\n');
                }
            } else {
                printf("(empty list or set)\n");
            }
            printc();
        });
    }

    function get(args) {
        args = args.join("");
        if (!hasArgs(args)) {
            errorArgs('get');
        } else $.get("/redis/get", {
            'arg': args
        }, function (r) {
            var data = r;
            if (data !== '') {
                printf(data + "\n");
            } else {
                printf("(nil)\n");
            }
            printc();
        });
    }

    function set(args) {
        if (!hasArgs(args.join(""))) {
            errorArgs('set');
        } else $.get("/redis/set", {
            'arg': args.join(",")
        }, function (r) {
            printf(r + "\n");
            printc();
        });
    }

    function del(args) {
        if (!hasArgs(args.join(""))) {
            errorArgs('set');
        } else $.get("/redis/del", {
            'arg': args.join(",")
        }, function (r) {
            printf(r + "\n");
            printc();
        });
    }

    function exists(args) {
        if (!hasArgs(args.join(""))) {
            errorArgs('exists');
        } else {
            $.get("/redis/exists", {arg: args.join(",")}, function (r) {
                var data = r;
                printf(data + "\n");
                printc();
            });
        }
    }

    function pttl(args) {
        args = args.join("");
        if (!hasArgs(args)) {
            errorArgs('pttl');
        } else {
            $.get("/redis/pttl", {arg: args}, function (r) {
                var data = r;
                printf(data + "\n");
                printc();
            });
        }
    }

    function pexpire(args) {
        if (!hasArgs(args.join(""))) {
            errorArgs('pexpire');
        } else {
            $.get("/redis/pexpire", {arg: args.join(",")}, function (r) {
                var data = r;
                printf(data + "\n");
                printc();
            });
        }
    }

    function help(arg) {
        arg = arg.join(" ");
        if (hasArgs(arg)) {
            switch (arg) {
                case "keys": {
                    printf(" KEYS pattern\n");
                    printf(" <span class='kw_a'>summary</span>: Find all keys matching the given pattern.\n");
                    printf(" <span class='kw_a'>since</span>: 1.0.0\n");
                    printf(" <span class='kw_a'>group</span>: generic\n");
                    break;
                }

                case "get": {
                    printf(" GET key\n");
                    printf(" <span class='kw_a'>summary</span>: Get the value of a key\n");
                    printf(" <span class='kw_a'>since</span>: 1.0.0\n");
                    printf(" <span class='kw_a'>group</span>: string\n");
                    break;
                }
                case "set": {
                    printf(" DEL key [key ...]\n");
                    printf(" <span class='kw_a'>summary</span>:  Delete a key\n");
                    printf(" <span class='kw_a'>since</span>: 1.0.0\n");
                    printf(" <span class='kw_a'>group</span>: generic\n");
                    break;
                }
                case "del": {
                    printf(" SET key value\n");
                    printf(" <span class='kw_a'>summary</span>: Set the string value of a key\n");
                    printf(" <span class='kw_a'>since</span>: 1.0.0\n");
                    printf(" <span class='kw_a'>group</span>: string\n");
                    break;
                }
                case "exists": {
                    printf(" EXISTS key [key ...]\n");
                    printf(" <span class='kw_a'>summary</span>: Determine if a key exists\n");
                    printf(" <span class='kw_a'>since</span>: 1.0.0\n");
                    printf(" <span class='kw_a'>group</span>: generic\n");
                    break;
                }
                case "pttl": {
                    printf(" PTTL key\n");
                    printf(" <span class='kw_a'>summary</span>: Get the time to live for a key in milliseconds\n");
                    printf(" <span class='kw_a'>since</span>: 2.6.0\n");
                    printf(" <span class='kw_a'>group</span>: generic\n");
                    break;
                }
                case "pexpire": {
                    printf(" PEXPIRE key milliseconds\n");
                    printf(" <span class='kw_a'>summary</span>: Set a key's time to live in milliseconds\n");
                    printf(" <span class='kw_a'>since</span>: 2.6.0\n");
                    printf(" <span class='kw_a'>group</span>: generic\n");
                    break;
                }
                default: {
                    printf(" no more messages about command `" + arg + "`\n");
                }
            }
        } else {
            printf(" Redis bash with SpringBoot by <a href='https://mrbird.cc' class='kw_b'>MrBird</a>, version v0.0.1 - Snapshot \n");
            printf(" These shell commands are defined internally.  Type `help` to see this list.\n");
            printf(" Type `help name` to find out more about the command `name`.\n");
            printf(" -------------------------------- \n");
            printf(" |       system command         | \n");
            printf(" -------------------------------- \n");
            printf(" clear    help    echo\n");
            printf(" -------------------------------- \n");
            printf(" |       redis  command         | \n");
            printf(" -------------------------------- \n");
            printf(" keys         get         set \n");
            printf(" del          exists        pttl\n");
            printf(" pexpire\n");
        }
        printc();
    }


    function processCommand() {
        var isValid = false;
        var args = command.split(" ");
        var cmd = args[0];
        args.shift();
        for (var i = 0; i < commands.length; i++) {
            if (cmd === commands[i].name) {
                commands[i].function(args);
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            printf("redis: command not found: `" + command + "`, type `help` for help.\n");
            printc();
        }
        commandHistory.push(command);
        historyIndex = commandHistory.length;
        command = "";
    }

    function displayPrompt() {
        printf('<span class="prompt">' + prompt + "</span> ");
        printf('<span class="path">' + path + "</span> ");
    }


    function appendCommand(str) {
        terminal.append(str);
        command += str;
    }

    $(document).keydown(function (e) {
        e = e || window.event;
        var keyCode = typeof e.which === "number" ? e.which : e.keyCode;
        if (keyCode === 8 && e.target.tagName !== "INPUT" && e.target.tagName !== "TEXTAREA") {
            e.preventDefault();
            if (command !== "") {
                erase(1);
            }
        }
        if (keyCode === 38 || keyCode === 40) {
            if (keyCode === 38) {
                historyIndex--;
                if (historyIndex < 0) {
                    historyIndex++;
                }
            } else if (keyCode === 40) {
                historyIndex++;
                if (historyIndex > commandHistory.length - 1) {
                    historyIndex--;
                }
            }
            var cmd = commandHistory[historyIndex];
            if (cmd !== undefined) {
                clearCommand();
                appendCommand(cmd);
            }
        }
    });
    $(document).keypress(function (e) {
        e = e || window.event;
        var keyCode = typeof e.which === "number" ? e.which : e.keyCode;
        switch (keyCode) {
            case 13: {
                printf("\n");
                processCommand();
                break;
            }

            default: {
                appendCommand(String.fromCharCode(keyCode));
            }
        }
    });

    function scroll(o) {
        o = o[0];
        o.scrollTop = o.scrollHeight;
    }

    function printf(msg) {
        terminal.append(msg);
    }

    function printc() {
        displayPrompt();
        historyIndex++;
        scroll(terminal);
    }

    function hasArgs(arg) {
        try {
            if (arg.length === 0) return 0;
            else return 1;
        } catch (e) {
            return 0;
        }
    }

    function errorArgs(args) {
        printf("(error) ERR wrong number of arguments for '" + args + "' command\n");
        printc();
    }

});