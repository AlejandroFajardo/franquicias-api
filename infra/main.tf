terraform {
  required_providers {
    mongodbatlas = {
      source  = "mongodb/mongodbatlas"
      version = "~> 1.16"
    }
  }
}

provider "mongodbatlas" {
  public_key  = var.public_key
  private_key = var.private_key
}

# Proyecto en Mongo Atlas
resource "mongodbatlas_project" "project" {
  name   = "franquicias-project"
  org_id = var.org_id
}

# Cluster (simulado para la prueba)
resource "mongodbatlas_cluster" "cluster" {
  project_id   = mongodbatlas_project.project.id
  name         = "cluster-franquicias"
  cluster_type = "REPLICASET"
  provider_name = "TENANT"
  backing_provider_name = "AWS"
  provider_region_name  = "US_EAST_1"
  provider_instance_size_name = "M0"
}