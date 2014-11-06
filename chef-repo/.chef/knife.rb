# See http://docs.getchef.com/config_rb_knife.html for more information on knife configuration options

current_dir = File.dirname(__FILE__)
log_level                :info
log_location             STDOUT
node_name                "chefuser"
client_key               "#{current_dir}/chefuser.pem"
validation_client_name   "ss-ds-validator"
validation_key           "#{current_dir}/ss-ds-validator.pem"
chef_server_url          "https://akshay-desktop/organizations/ss-ds"
cache_type               'BasicFile'
cache_options( :path => "#{ENV['HOME']}/.chef/checksums" )
cookbook_path            ["#{current_dir}/../cookbooks"]
