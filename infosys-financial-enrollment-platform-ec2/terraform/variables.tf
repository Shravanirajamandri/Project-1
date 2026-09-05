variable "region" { default = "us-east-1" }
variable "instance_type" { default = "t3.medium" }
variable "project_name" { default = "infosys-financial-enrollment-platform-ec2" }
variable "key_name" { description = "Existing EC2 key pair name" type = string }
