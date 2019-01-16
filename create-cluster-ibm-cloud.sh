bx login

# Create Cluster
bx cs cluster-create --name gas-stations

# Cluster status
bx cs workers gas-stations

# kubectl
$( bx cs cluster-config gas-stations | grep export )