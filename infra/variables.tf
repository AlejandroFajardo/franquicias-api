variable "public_key" {
  description = "MongoDB Atlas Public Key"
  type        = string
}

variable "private_key" {
  description = "MongoDB Atlas Private Key"
  type        = string
  sensitive   = true
}

variable "org_id" {
  description = "MongoDB Atlas Organization ID"
  type        = string
}