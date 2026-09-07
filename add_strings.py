import xml.etree.ElementTree as ET
from xml.dom import minidom

file_path = 'app/src/main/res/values/strings.xml'
tree = ET.parse(file_path)
root = tree.getroot()

new_strings = {
    "nav_gpf": "GPF",
    "nav_salary_set": "Salary Set",
    "nav_api_key": "API Key",
    "nav_about": "About"
}

existing_names = [child.get('name') for child in root.findall('string')]

for name, value in new_strings.items():
    if name not in existing_names:
        elem = ET.SubElement(root, 'string', name=name)
        elem.text = value

xmlstr = minidom.parseString(ET.tostring(root)).toprettyxml(indent="    ")
# Remove empty lines minidom adds
xmlstr = '\n'.join([line for line in xmlstr.split('\n') if line.strip()])

with open(file_path, 'w') as f:
    f.write(xmlstr)
