Exrecise System in SUN RPC : Remote Procedure Call 

The objective of the machine problem is to write a network management application that tracks user logins, CPU usage and other statistics
on a host and allows querying by a RPC-based network management system. Its output can be used to feed into an analysis component for
deciding on corrective actions in self-managing distributed systems.

The system will have multiple clients and single/multiple servers. Clients can send request to a server running at a different machine to
get the current system statistics of the server machine. It tracks, for example:

1. Current system time (can be in different formats such as date, time, or a combination of both.)
2. CPU usage
3. Memory usage
4. Load procs per minute

The running system will consist of client and server program. The platform used are the UNIX/Linux workstations, and is developed in C
programming language.

The program prints all the statistics of the host system and it's output is used to analyse and further take decisions to correct or
monitor host.  
