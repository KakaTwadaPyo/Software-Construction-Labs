#include <iostream>
#include <vector>
#include <cstring>
#include <string>
#include <climits>
#include <algorithm>
#include <queue>
#include <stack>
using namespace std;

//Disjkstra algoritjm does not work n negative weights
class Dijkstra {
    public:
    /*
    in adj list we have a 3d array the outer most index is the current node 
    the inner 2d array represents like this 
    inner2d array first column is = connected edge or neighbour
    innder2d array 2nd column is = conected edge or neighbours distance
    so each most outer index shows the 1 node and its 2d array represents its connected neighbour nodes and their distances
    */
    //here adj is kind of a 3d array
    //TC = O(n^2)
    vector <int> dijk (int S , vector<vector<int>> adj[] , int V) { // Here v is the number of vertices and S is the sourse from where we start
    //vector<datatype> vec(size, initial_value); this down is according to this
        vector<bool> Explored(V , 0); // means no edge is explored 0 means false
        vector<int> distance (V , INT_MAX); // means the distance of all edges is infintie
        int count = V;
        distance[S] = 0; // means the distance of soruse from itself is zero

        // We have to run the loop edge number of times
        while (count--) {

            //Select the node that is not explored yet
            int node = -1 , value = INT_MAX;
            for (int i = 0; i < V; i++) {
                if (!Explored[i] && distance[i] < value) {
                    node = i;
                    value = distance[i]; // this stores the value of distance in value
                }
            }
            Explored[node] = 1; // this turns the node true so now it is explored

            //Now next step is to visit the neighbours or relax the edges
            for (int i = 0; i < adj[node].size(); i++) { // means being specific for the current nodes neighbours like outer indexs inner 2d array neighbours
                int neighbour = adj[node][i][0]; // here node represents a current edge, [i] represents the connected edge or the neighbour 
                //of current node means the first column of inner 2d array and 0 means the distance 
                int weight = adj[node][i][1];
                if (!Explored[neighbour] && weight + distance[node] < distance[neighbour]) { //  weight means the current weight of node and + its distance from neighbour 
                    distance[neighbour] = weight + distance[node];
                }
            }
        }
        return distance; 
    }
    //this is same implementation but now with priority queue
    //the advantange here is the minheap automatically gives us the shorted distance node on top and we wont have to run a loop n times to get it
    //TC = O(E log V) or O(n log n)
    vector <int> dijkwitPqueue (int S , vector<vector<int>> adj[] , int V) { // Here v is the number of vertices and S is the sourse from where we start
    //vector<datatype> vec(size, initial_value); this down is according to this
        vector<bool> Explored(V , 0); // means no edge is explored 0 means false
        vector<int> distance (V , INT_MAX); // means the distance of all edges is infintie
        distance[S] = 0; // means the distance of soruse from itself is zero
        priority_queue<pair <int , int > , vector <pair <int , int>> , greater<pair <int , int>>> p; // this is creating a min heap
        // while this priority_queue<pair<int , int>> pq; is a maxheap by default so for dijkstra we use min heap

        // as now we are using priority queue we will run this loop until our queue becomes empty
        p.push({0 , S}); //first initializing our pq first is the distance and second is the node

        while (!p.empty()) {
            //now first step is to pop a node
            int node = p.top().second; // we are geting the node not the distance 
            p.pop();
            //as it is a priority queue so the node with minimum distance is popped first and is at top

            if (Explored[node]) {
                continue;
            }

            Explored[node] = 1; // this turns the node true so now it is explored

            //Now next step is to visit the neighbours or relax the edges
            for (int i = 0; i < adj[node].size(); i++) { // means being specific for the current nodes neighbours like outer indexs inner 2d array neighbours
                int neighbour = adj[node][i][0]; // here node represents a current edge, [i] represents the connected edge or the neighbour 
                //of current node means the first column of inner 2d array and 0 means the distance 
                int weight = adj[node][i][1];
                if (!Explored[neighbour] && weight + distance[node] < distance[neighbour]) { //  weight means the current weight of node and + its distance from neighbour 
                    distance[neighbour] = weight + distance[node];
                    p.push({distance[neighbour] , neighbour});
                }
            }
        }
        return distance; 
    }
};

/*Now which method to prefer
if we have a dense graph then there we will have E = v^2 so that means each edge will be pushed inside queue v times so total TC will be O(V^2 log v)
or O(n^2 log n) in that conditoin we will prefer the first method without the queue one but if we dont have a complete graph = dense graph then 
this method is prefered
means for sparse graph 2nd approach is better with queue */
int main () {
    int V = 4;
    vector<vector<int>> adj[V]; // creating a graph
    adj[0].push_back({1 , 1});
    adj[0].push_back({2 , 9});
    adj[1].push_back({2 , 4});
    adj[1].push_back({3 , 5});
    adj[2].push_back({3 , 8});
    Dijkstra d;
    vector<int>dista = d.dijk(0 , adj , V);
    int totalDis =0;
    cout << "Distance of node from Sourse from other edges" << endl;
    cout << 0 << ": "; 
     for (int i = 0; i < V; i++) {
        cout << "To node " << i << ": ";
        if (dista[i] == INT_MAX)
            cout << "INF\n";
        else
            cout << dista[i] << '\n';
    }
    cout << endl;
    for (int elements : dista) {
        totalDis += elements;
    }

    cout << "Total Shortest Distance is " << totalDis << endl;

    return 0;
}
