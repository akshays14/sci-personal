#!/usr/bin/python
# -*- coding: utf-8 -*-

import MySQLdb as mdb
import sys

if len(sys.argv) != 2:
	print "Please provide number of regions for hbase table"
	sys.exit(1)

num_of_parts = int(sys.argv[1])
try:
	con = mdb.connect('localhost', 'readonly', 'readonlySQL', 'core_db');

	cur = con.cursor()
	cur.execute("SELECT MIN(id) FROM paper")
	start = cur.fetchone()[0]
	cur.execute("SELECT MAX(id) FROM paper")
	end = cur.fetchone()[0]
except mdb.Error, e:
	print "Check database connection parameters"
	sys.exit(1)
finally:
	if con:
		con.close()


partitions = []
part_size = (end-start)/num_of_parts

for i in xrange(start, end, part_size):
	partitions.append(str(i))

print partitions
