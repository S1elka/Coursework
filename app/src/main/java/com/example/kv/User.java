package com.example.kv;

    public class User {
        public String id, usname, pass, adult;


        public User() {

        }

        public User(String id, String usname, String pass, String adult) {
            this.id = id;
            this.usname = usname;
            this.pass = pass;
            this.adult = adult;
        }

        public User(String id, String usname, String pass) {
            this.id = id;
            this.usname = usname;
            this.pass = pass;
            this.adult = "1";
        }

        public String getPass(){
            return this.pass;
        }
    }
