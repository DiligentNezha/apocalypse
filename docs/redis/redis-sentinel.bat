start cmd /k "title sentinel-7301 && cd master-6301 && redis-server.exe sentinel.conf --sentinel"
start cmd /k "title sentienl-7302 && cd slave-6302 && redis-server.exe sentinel.conf --sentinel"
start cmd /k "title sentinel-7303 && cd slave-6303 && redis-server.exe sentinel.conf --sentinel"
