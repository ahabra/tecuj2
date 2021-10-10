# Technology Exponent Common Utilities - Version 2


Common Java utilities for everyday programming, compiled with Java 11.
* Author: Abdul Habra

The library contains classes grouped by package

## `collection` Classes

### `CollectionTools`
Helper function to handle collections.

* `copy(C from, C to)`: Copy a collection to another of the same type
* `C copy(C from)`: Make a copy of a collection
* `boolean contains(Collection<T> col, T target, BiPredicate<T,T> matcher)`: Check
  if a collection contains a target value using given matcher
* `boolean contains(Collection<T> col, Predicate<T> matcher)`: Check if a
  collection contains any value that matches.
* `boolean isEmpty(Collection<T> c)`: Check a collection is null or empty
* `Map<K, V> toMapByKey(String key, Collection<V> col)`:  Convert a collection
  to a map using the given field's name as key
* `Map<K, V> toMapByKey(String key, V... col)`: Convert a collection
  to a map using the given field's name as key

### `ListOfPairs`
A list where each item is a pair (tuple)

### `ListSearcher`
Handy functions to search lists including `indexOf`, `lastIndexOf`, `indexOfAny`,
`indexOfSubList`, and others.

### `ListTools`
List-specific functions including `getLast`, `setLast`, `slice`, `subList`,
`left`, and `right`.

## `file` Classes

### `FileTools`
File handling functions

* `remove(String filePath)`: remove a file
* `write(String filePath, String text)`: write text to given file
* `append(String filePath, String text)`: append text to given file
* `String readAsString(String filePath)`: read content of given file as a string
* `List<String> readLines(String filePath)`: read content of given file as a list of lines

### `ResourceTools`
Resource handling functions

* `InputStream readAsInputStream(String resourceName)`: Read content of a resource 
  to an input stream
* `String readAsString(String resourceName)`: Read content of a resource as a string
* `Properties readAsProperties(String resourceName)`: Read content of a resource into
  a `Properties` object
* `List<String> readLines(String resourceName)`: read content of given resource as a list of lines

### `YamlTools`
Yaml files handling

* `Map<String, String> readFile(String resourceName)`: Read a yaml file from resources into a Map.

## `math` Classes
Math related classes

### `FixedDecimal`
Represent an exact decimal number

### `FixedDecimalBuilder`

### `HumanNumber`
Represent a number in an easy to read string

## `reflection`
Reflection related classes

### `FieldReflector`
Access the fields of an object

### `MethodReflector`
Access the methods of an object

### `PropertyReflector`
Access the properties of an object

* `<R> R get(String propertyName)`: get the value of a property, including nested
  properties, for example `customer.address.getCity`

### `Reflector`
Access fields, methods, and properties on an object

### `ScopeEnum`
An enumeration of possible scopes: `private`, `protected`, `public`, and `package`.

## `string`
String handling classes

### `EscapeTools`
* `String escapeUrl(String s)`: Escape a url using utf8.
* `String unescapeUrl(String s)`: Unescape a url using utf8.

### `StringSearcher`
Search a string

### `StringTools`
* `String replaceBetween(String text, String start, String end, String with)`:
  replace a substring between a start and end markers.
* `String clearBetween(String text, String start, String end)`:
  remove a substring between a start and end markers.

### `ToString`
Make it easy to convert an object to a string

## `time`
Time handling classes

### `Time`
Represent time as a tuple of (year, month, day, hour, minute, second, nano).
